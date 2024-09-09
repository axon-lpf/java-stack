package com.axon.java.stack.netty.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 将buffer 转换成只读的buffer
 *
 *
 * 将 ByteBuffer 转换为只读缓冲区 (asReadOnlyBuffer()) 主要用于场景中保护数据的完整性，确保缓冲区的数据不会在某些场景下被修改。这种转换的意义在于，允许共享同一块数据的多个组件（线程或模块）对其进行安全的读取，而无需担心某一方会无意或恶意地修改数据。
 *
 * 转换为只读缓冲区的意义：
 *
 * 	1.	保护数据：只读缓冲区能够确保缓冲区中的数据不被修改。如果你的数据一旦写入后需要保持不变，转换为只读的缓冲区可以避免意外的修改操作。
 * 	2.	共享数据：当多个线程或组件需要访问同一块数据时，使用只读缓冲区可以保证数据一致性，不会因为意外修改而引发错误或数据不一致的问题。
 * 	3.	封装与安全性：只读缓冲区可以用作 API 的返回值，向调用方提供数据的同时，防止其修改数据，从而提升系统的封装性和安全性。
 *
 * 使用场景：
 *
 * 	1.	缓存场景：当一些缓冲数据只需要读取但不需要修改时，使用只读缓冲区可以避免意外修改。例如，缓存某些文件内容或者数据片段。
 * 	2.	多线程读取：在多线程环境中，如果有多个线程同时需要读取相同的数据，将 ByteBuffer 转换为只读的，可以确保数据不会因为并发访问而被修改。
 * 	3.	API 设计：当设计 API 时，使用只读缓冲区可以确保调用者无法修改你提供的底层数据，这样可以避免可能的不正确行为或安全问题。
 * 	4.	日志系统：在日志系统中，将原始数据（如日志记录、网络数据包等）转换为只读缓冲区，确保数据不会因为日志系统中的操作而被修改，确保审计和追溯的可靠性。
 *
 *
 *
 */
public class BufferConvertBufferReadOnly {

    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        for (int i = 0; i < 64; i++) {
            byteBuffer.put((byte) i);
        }

        byteBuffer.flip();

        // 转换成只读的buffer
        ByteBuffer asReadOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(asReadOnlyBuffer.getClass());

        while (asReadOnlyBuffer.hasRemaining()) {

            System.out.println(asReadOnlyBuffer.get());
        }

        System.out.println("运行结束");


        // 模拟多线程读取
        new Thread(() -> {
            while (asReadOnlyBuffer.hasRemaining()) {
                System.out.println("线程1 读取: " + asReadOnlyBuffer.get());
            }
        }).start();

        new Thread(() -> {
            while (asReadOnlyBuffer.hasRemaining()) {
                System.out.println("线程2 读取: " + asReadOnlyBuffer.get());
            }
        }).start();
    }
}
