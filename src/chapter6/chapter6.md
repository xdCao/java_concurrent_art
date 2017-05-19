#第六章 Java并发容器和框架

##一、ConcurrentHashMap

###1.

为什么使用：HashMap不安全，HashTable效率低

>这边得上网查

HashMap:并发执行put操作时会引起死循环，因为多线程会导致HashMap的Entry链表形成环形数据结构，Entry
的next节点永远不为空，就会产生死循环获取Entry

HashTable:HashTable使用synchronized来保证线程安全，在线程竞争激烈的情况下其效率十分低下

ConcurrentHashMap：使用锁分段技术：首先将数据分成一段一段地存储，然后给每一段数据配一把锁，
当一个线程占用锁访问其中一个段数据的时候，其他段的数据也能被其他线程访问。

###2.ConcurrentHashMap的结构

Segment数组+HashEntry数组

segment是一种可重入锁，HashEntry用于存储键值对数据

一个segment里包含一个HashEntry数组，每个segment守护着一个HashEntry数组里的元素，
当对HashEntry数组的数据进行修改时，必须首先获得与它对应的segment锁

###3、4初始化和定位segment

这里主要讲了hash的散列算法之类的，目前不用去研究

###5.ConCurrentHashMap的操作

（1）get

整个get过程不需要加锁，除非读到的值是空的才会加锁重读。

做法：get方法里将要使用的共享变量都定义成volatile类型。定义成volatile的变量，能够在线程之间保持可见性，
能够被多线程同时读，并且保证不会读到过期的值，但是只能被单线程写。

    之所以不会读到过期的值，是因为根据java内存模型的happens before原则，
    对volatile变量的写入操作先于读操作。
    
>上面这是用volatile替换锁的经典应用场景。

（2）put

加锁、扩容、定位添加

（3）size

先尝试两次不加锁的统计（直接累加各segment的count），比对modcount变量看是不是发生变化，如果发生变化再通过加锁的方式统计。

##二、ConcurrentLinkedQueue



    