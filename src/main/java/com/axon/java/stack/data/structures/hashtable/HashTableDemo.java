package com.axon.java.stack.data.structures.hashtable;

import lombok.Data;

/**
 * 自定义hashTable的实现
 */
public class HashTableDemo {

    public static void main(String[] args) {

        HashTable hashTable = new HashTable(7);
        hashTable.addEmp(new Emp(1, "剑兰"));
        hashTable.addEmp(new Emp(2, "星火"));
        hashTable.addEmp(new Emp(3, "安迪"));
        hashTable.addEmp(new Emp(4, "林书"));
        hashTable.addEmp(new Emp(5, "三七"));
        hashTable.addEmp(new Emp(6, "拾月"));
        hashTable.addEmp(new Emp(7, "欧元"));
        hashTable.addEmp(new Emp(8, "锦瑟"));
        hashTable.addEmp(new Emp(9, "浮生"));
        //循环遍历列表
        hashTable.list();

        hashTable.findEmpById(2);


    }

}

/**
 * 定义对hashtable的增删改查
 */
class HashTable {

    // 数组的最大元素
    private int maxSize;

    private EmployeeLinkList[] employees;

    public HashTable(int maxSize) {
        this.maxSize = maxSize;
        //初始化集合
        employees = new EmployeeLinkList[maxSize];
        for (int i = 0; i < employees.length; i++) {
            employees[i] = new EmployeeLinkList();
        }
    }

    /**
     * 添加员工
     *
     * @param emp
     */
    public void addEmp(Emp emp) {

        int index = getIndex(emp.getId());
        employees[index].addEmp(emp);
    }

    /**
     * 通过id去查找
     *
     * @param id
     */
    public void findEmpById(int id) {
        int index = getIndex(id);
        employees[index].findById(id);
    }

    /**
     * 打印出每个数组中对应的链表
     */
    public void list() {

        for (int i = 0; i < employees.length; i++) {
            employees[i].list(i);
        }
    }


    /**
     * 获取数组下标索引
     *
     * @param id
     * @return
     */
    public int getIndex(int id) {
        return id % maxSize;
    }


}

/**
 * 定义对linkList的增删改查
 */
class EmployeeLinkList {

    /**
     * 定义头节点
     */
    private Emp head;

    /**
     * 添加元素
     *
     * @param emp
     */
    public void addEmp(Emp emp) {
        if (head == null) {
            head = emp;
            return;
        }
        Emp temp = head;
        while (true) {
            if (temp.getNext() == null) {
                break;
            }
        }
        temp.setNext(emp);
    }

    public void list(int no) {
        if (head == null) {
            System.out.println("编号" + no + "当前链表为空");
            return;
        }
        Emp temp = head;
        while (true) {
            System.out.println("编号" + no + "empName:" + temp.getName());
            if (temp.getNext() == null) {
                break;
            }
            temp = temp.getNext();
        }
    }

    /**
     * 根据 id查找员工信息
     *
     * @param id
     */
    public void findById(int id) {

        if (head == null) {
            System.out.println("当前链表为空");
            return;
        }
        Emp temp = head;
        while (true) {
            if (temp.getId() == id) {
                System.out.println("empName:" + temp.getName());
                break;
            }
            if (temp.getNext() == null) {
                break;
            }
            temp = temp.getNext();
        }
    }

}


/**
 * 定义员工信息
 */
@Data
class Emp {

    private int id;

    private String name;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }


    /**
     * 下一个节点
     */
    private Emp next;

}
