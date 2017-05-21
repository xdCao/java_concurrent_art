#第七章 Java中的13个原子操作类

包含4种类型的原子更新方式：原子更新基本类型、原子更新数组、原子更新引用、原子更新属性（字段）

##一、原子更新基本类型

AtomicBoolean,AtomicInteger,AtomicLong

其实就是用CAS实现，也可以采取源码中的思路自己写char、float、double的原子类

##二、原子更新数组

AtomicLongArray,AtomicReferenceArray,AtomicIntegerArray

注意：上述三种会将传入的数组复制一份，当其对内部的数组进行修改时，不会影响到传入的数组

##三、原子更新引用类型

AtomicReference,AtomicReferenceFieldUpdater,AtomicMarkableReference

##四、原子更新字段类

创建更新器、

更新类的字段必须使用volatile

AtomicIntegerFieldUpdater,AtomicLongfieldUpdater,AtomicStampedReference