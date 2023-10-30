package org.abc.matrix.commons.tasks.transfer;


import org.abc.matrix.commons.lang.result.BaseResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 数据传输工具,用于读取文件系统数据投放到队列中,由多线程去消费
 * <p>
 * 基于阻塞队列的生产者消费者模式
 * <p>
 * <p>
 * Created by wanjia on 16/11/18.
 */
public class DataTransfer<T> {

    private static final Logger logger = LoggerFactory.getLogger(DataTransfer.class);
    private RowdataProcessor<T> rowdataProcessor;
    private String filePath;
    private int defaultThNum = Runtime.getRuntime().availableProcessors();


    private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(10000);
    private AtomicLong success = new AtomicLong(0);
    private AtomicLong fail = new AtomicLong(0);


    public DataTransfer() {
    }

    /**
     * 设置数据
     *
     * @param rowdataProcessor
     * @param filePath
     */
    public DataTransfer(RowdataProcessor<T> rowdataProcessor, String filePath) {
        this.rowdataProcessor = rowdataProcessor;
        this.filePath = filePath;
    }

    /**
     * 设置处理器
     *
     * @param rowdataProcessor
     */
    public void setRowdataProcessor(RowdataProcessor<T> rowdataProcessor) {
        this.rowdataProcessor = rowdataProcessor;
    }

    /**
     * 设置文件路径
     *
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 开始执行处理程序
     *
     * @param thNum 使用多少个线程去处理
     */
    public void start(int thNum) {
        if (null == rowdataProcessor) {
            throw new IllegalStateException("rowdataProcessor cannot be null!!!");
        }

        if (thNum > 0 && thNum < 50) {
            defaultThNum = thNum;
        }

        long st = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(defaultThNum);
        final File file = new File(filePath);
        if (!file.exists()) {
            logger.error("文件不存在!!!");
            return;
        }
        //start read file
        new Thread(new Runnable() {
            @Override
            public void run() {
                startReadFile(file);
            }
        }).start();
        for (int i = 0; i < defaultThNum; i++) {
            new Thread(new ProcessorRun(i, latch)).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.error("", e);
            Thread.currentThread().interrupt();
        }
        logger.error("处理完成,成功处理：" + success.get() + " 失败数：" + fail.get() + "耗时:" + (System.currentTimeMillis() - st) / 1000 + "秒");
    }

    /**
     * 读取文件
     *
     * @param file
     */
    private void startReadFile(File file) {
        BufferedReader reader = null;
        long readLine = 0;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                if (readLine % 10000 == 0) {
                    logger.error("已经读取:" + readLine + "行数据...");
                }
                try {
                    if (StringUtils.isNotBlank(tempString))
                        queue.put(tempString);
                } catch (InterruptedException e) {
                    logger.error("error Interruped!", e);
                    continue;
                }
                readLine++;
            }
            reader.close();
            logger.error("文件已经读取完毕：" + readLine + "行....");
        } catch (IOException e) {
            logger.error("读取文件失败:", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    logger.error("", e1);
                }
            }
        }
    }

    class ProcessorRun implements Runnable {

        private int index;
        private CountDownLatch latch;
        private long count = 0;

        public ProcessorRun(int index, CountDownLatch latch) {
            this.index = index;
            this.latch = latch;
        }

        @Override
        public void run() {
            //先等个几秒，让数据预热一下
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.error("", e);
                Thread.currentThread().interrupt();
            }
            long st = System.currentTimeMillis();
            logger.error("start index:" + index + " start:" + st + "  isEmpty():" + queue.isEmpty());
            while (!queue.isEmpty()) {
                BaseResult<T> result = null;
                try {
                    String line = queue.poll(10, TimeUnit.MILLISECONDS);
                    result = rowdataProcessor.process(line);
                } catch (Exception e) {
                    logger.error("", e);
                }
                if (null == result) {
                    continue;
                }

                if (result.isSuccess()) {
                    success.incrementAndGet();
                } else {
                    fail.incrementAndGet();
                }

                count++;
                if (count % 1000 == 0) {
                    logger.error("Thread:" + index + " 已经处理了:" + count + "条记录! 共耗时:" + (System.currentTimeMillis() - st) / 1000 + "秒" + " 当前成功数:" + success.get() + " 当前失败数:" + fail.get());
                }
                try {
                    Thread.sleep(5);//目的是为了等待数据load
                } catch (InterruptedException e) {
                    logger.error("", e);
                }
            }
            logger.error("Thread:" + index + " 处理完成，一共处理：" + count + "条 耗时:" + (System.currentTimeMillis() - st) / 1000 + "秒");
            //处理完成
            latch.countDown();
        }
    }

}
