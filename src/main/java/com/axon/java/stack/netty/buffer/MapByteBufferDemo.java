package com.axon.java.stack.netty.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * 这个 MapByteBufferDemo 代码展示了 Java NIO 中的 MappedByteBuffer 的使用。MappedByteBuffer 是一种高效的 I/O 操作方式，允许将文件的某一部分直接映射到内存中，操作文件的内容就像操作内存一样，可以在不通过常规 I/O 流读写的情况下，直接对文件数据进行读取和修改。
 *
 * 代码的含义：
 *
 * 	1.	RandomAccessFile: 用于以读写（rw）模式打开文件 1.txt。
 * 	2.	FileChannel: 通过 RandomAccessFile 获取一个 FileChannel，用来与文件进行交互。
 * 	3.	MappedByteBuffer:
 * 	•	使用 fileChannel.map() 方法将文件的前 9 个字节映射到内存中。
 * 	•	map(FileChannel.MapMode.READ_WRITE, 0, 9)：表示将文件从 0 到 9 字节的部分映射到内存中，且以读写模式（READ_WRITE）操作。
 * 	4.	mappedByteBuffer.put():
 * 	•	put(0, (byte) 'H')：将文件的第一个字节修改为 'H'。
 * 	•	put(3, (byte) '9')：将文件的第四个字节修改为 '9'。
 * 	5.	randomAccessFile.close(): 关闭文件，释放资源。
 *
 * 映射后的内容更改不需要再显式写入文件，修改会自动同步到磁盘文件中。
 *
 * 使用场景：
 *
 * MappedByteBuffer 通常用于高效处理大文件或需要频繁读写文件的一部分数据的场景。它具有以下优点和使用场景：
 *
 * 	1.	处理超大文件：
 * 	•	当文件太大无法一次加载到内存中时，可以将文件的一部分映射到内存，分段处理数据，避免内存溢出。
 * 	•	例如处理超大日志文件、数据库文件或视频文件等。
 * 	2.	减少 I/O 开销：
 * 	•	传统的 I/O 需要在用户空间和内核空间之间频繁切换，而 MappedByteBuffer 直接将文件映射到内存，从而减少了不必要的 I/O 操作，提高了效率。
 * 	3.	并发读取或修改文件：
 * 	•	多线程程序可以通过映射文件不同区域，实现并发处理文件的不同部分，而无需担心线程间的 I/O 竞争。例如在并发处理大文件的读取、分析和修改场景中，MappedByteBuffer 可以显著提升性能。
 * 	4.	随机访问文件：
 * 	•	在某些场景下需要快速定位和修改文件中的某些内容，比如对数据库文件或索引文件的修改和读取，MappedByteBuffer 提供了与内存操作相似的随机访问特性。
 *
 * 使用 MappedByteBuffer 的优缺点：
 *
 * 优点：
 *
 * 	•	高效：避免了频繁的系统调用和内存复制。
 * 	•	大文件处理能力强：能够处理比内存大的文件。
 * 	•	随机访问能力：适合频繁的文件定位和修改。
 *
 * 缺点：
 *
 * 	•	资源消耗：由于是通过内存映射文件，占用的内存空间不小，对于大量文件映射可能消耗大量内存。
 * 	•	文件同步问题：虽然操作完成后会自动同步到文件，但同步时间点并不确定，可能会有些延迟。
 *
 * 总结：
 * MappedByteBuffer 是一种高效处理文件数据的方式，特别适用于需要频繁读写、随机访问或处理超大文件的场景。
 */
public class MapByteBufferDemo {

    public static void main(String[] args) throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt","rw");

        FileChannel fileChannel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 9); //表示将文件从 0 到 9 字节的部分映射到内存中，且以读写模式（READ_WRITE）操作。

        mappedByteBuffer.put(0, (byte) 'H');  //put(0, (byte) 'H')：将文件的第一个字节修改为 'H'。
        mappedByteBuffer.put(3, (byte) '9'); //put(3, (byte) '9')：将文件的第四个字节修改为 '9'。


        randomAccessFile.close();

        System.out.println("结束");
    }
}
