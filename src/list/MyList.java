package list;


public class MyList {

    /**
     * 头节点的插入
     */

    public static void headinsert(ListNode head ,ListNode newhead){
        ListNode old = head;
        head = newhead;
        head.next = old;
    }

    /**
     * 尾节点的插入
     */

    public static void tailinsert(ListNode tail,ListNode newtail){

        ListNode old = tail;
        tail = newtail;
        tail.next = null;
        old.next = tail;

    }

    /**
     * 遍历
     */

    public static void traverse(ListNode head){
        while (head != null){
            System.out.print(head.value + " ");
            head = head.next;
        }
        System.out.println();
    }

    /**
     * 查找
     */
    public static int find(ListNode head,int value){
        int index = -1;
        int count = 0;
        while (head != null){
            if(head.value == value){
                index = count;
                return index;
            }
            count++;
            head = head.next;
        }
        return index;
    }
    /**
     * 插入
     */
    public static void insert(ListNode p,ListNode s){
        ListNode next = p.next;
        p.next = s;
        s.next = next;

    }

    /**
     * 删除
     */
    public static void delete(ListNode head,ListNode q){
        if (q != null && q.next != null){
            ListNode p = q.next;
            q.value = p.value;
            //删除q.next
            q.next = p.next;
            p = null;
        }
        //删除最后一个元素的情况
        if (q.next == null){
            while (head != null){
                if (head.next != null && head.next == q){
                    head.next = null;
                    break;
                }
                head = head.next;
            }
        }
    }

    public static void main(String[] args) {
        ListNode  node1 = new ListNode(1);
        ListNode  node2 = new ListNode(2);
        ListNode  node3 = new ListNode(3);
        node1.next = node2;
        node2.next = node3;
        node3.next = null;
        traverse(node1);

        ListNode newhead = new ListNode(0);
        headinsert(node1,newhead);
        traverse(newhead);

        ListNode newtail = new ListNode(4);
        tailinsert(node3,newtail);
        traverse(newhead);

        System.out.println(find(newhead,3));

        ListNode node = new ListNode(5);
        insert(node3,node);
        traverse(newhead);

        delete(newhead,node3);
        traverse(newhead);
    }
}
