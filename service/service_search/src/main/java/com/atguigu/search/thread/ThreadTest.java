package com.atguigu.search.thread;

import java.util.concurrent.*;

/**线程测试类
 * */
public class ThreadTest {
    //创建线程池 确保系统中 止只有一到三个线程池  只要有异步任务 就提交给线程池 让它自己去执行
 public static   ExecutorService service = Executors.newFixedThreadPool(10);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main方法开始执行了");
        //启动thread01 线程
        Thread01 thread01 = new Thread01();
        thread01.start();

        //启动Thread01 线程
        Runable01 runable01 = new Runable01();
        new Thread(runable01).start();

        //启动Callable01 线程
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable01());
        new Thread(futureTask).start();
        Integer integer = futureTask.get();
        System.out.println("线程返回的结果是："+integer);

        System.out.println("main方法结束执行了");

        //线程池 去执行线程
        service.execute(new Runable01()); //提交给线程池 去执行

        //手动创建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                5,  //核心线程数
                20,  //最大线程数
                10, //最大存活时间
                TimeUnit.SECONDS, // 时间单位
                new LinkedBlockingDeque<>(100000), //阻塞队列 默认是Integer的最大长度 需要手动指定长度 防止资源耗尽
                new ThreadPoolExecutor.AbortPolicy()); // 拒绝策略    1、丢弃当前   2、丢弃最老的线程  默认是丢弃当前的线程任务

//        Executors.newCachedThreadPool(); //core是0  所有都可以回收
//        Executors.newFixedThreadPool(10); //固定大小 core =最大
//        Executors.newScheduledThreadPool(10); //定时任务的线程池
//        Executors.newSingleThreadExecutor(); //单线程的线程池 后台从队列中获取任务 挨个执行



    }


//1、继承Thread
    public static class Thread01 extends  Thread{
        @Override
        public void run(){
            System.out.println("Thread01当前线程："+ Thread.currentThread().getId());
            int i=10/2;
            System.out.println("Thread01运行结果"+i);
        }
    }

//    2、实现Runable接口
    public static class Runable01 implements Runnable{
    @Override
    public void run() {
        System.out.println("Runable01当前线程："+ Thread.currentThread().getId());
        int i=10/2;
        System.out.println("Runable01运行结果"+i);
    }
}

// 3、实现Callable接口
    public static class Callable01 implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("Callable01当前线程："+ Thread.currentThread().getId());
        int i=10/2;
        System.out.println("Callable01运行结果"+i);
        return i;
    }
}

}
