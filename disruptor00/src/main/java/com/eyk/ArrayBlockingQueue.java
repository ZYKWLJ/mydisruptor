package com.eyk;

public class ArrayBlockingQueue<E> {
    final Object[] items;
    int count;
    int takeIndex;// 读指针
    int putIndex;// 写指针

    public ArrayBlockingQueue(int capacity) {
        this.items = new Object[capacity];
    }

    // 存数据，不限时阻塞
    public void put(E e) throws InterruptedException {
        while (count == items.length) {
            System.out.println("如果队列满了，生产者线程就在此阻塞");
        }
        System.out.println("队列有位置了，生产者线程在此被唤醒，继续执行把数据放入数组中");
        enqueue(e);// 数据入列
    }

    // 取数据，不限时阻塞
    public E take(E e) throws InterruptedException {
        while (count ==0) {
            System.out.println("如果队列空了，消费者线程就在此阻塞");
        }
        System.out.println("队列有数据了，消费线程在此被唤醒，继续执行从数组中取数据");
        return dequeue();// 取出数据
    }

    private void enqueue(E e) {
        final Object[] items = this.items;
        // 把数据放在写指针的索引位置
        items[putIndex] = e;
        // 写指针+1，然后判断是否满载
        if (++putIndex == items.length) {
            // 满载就重置写指针
            putIndex = 0;
        }
        // 存储个数+1
        count++;
    }

    private E dequeue() {
        final Object[] items = this.items;
        E e = (E) items[takeIndex];// 取出写指针地方的数据
        items[takeIndex] = null;// 取出数据后位置置为null
        if (++takeIndex == items.length) {
            takeIndex = 0;
        }
        count--;// 数组中存储数据的个数减1
        return e;// 返回要取走的数据
    }

}
