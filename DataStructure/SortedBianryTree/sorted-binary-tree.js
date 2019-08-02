
/**
 * 排序二叉树
 */
class SortedBinaryTree {
    constructor(key) {
        this.key = key;
        this.left = null;
        this.right = null;
    }

    /**
     * 添加子节点，比自己Key小就添加到左边，相反添加到右边
     * @param {SortedBinaryTree} node 子节点
     */
    addChild(node) {
        if (this.key > node.key) {
            if (this.left === null) {
                this.left = node;
            } else {
                this.left.addChild(node);
            }
        } else if (this.key < node.key) {
            if (this.right === null) {
                this.right = node;
            } else {
                this.right.addChild(node);
            }
        }
    }

    /**
     * 中序遍历，从小到大遍历
     * @param {function} callback 回调函数
     */
    inOrderTraversal(callback) {
        if (this.left !== null) {
            this.left.inOrderTraversal(callback);
        }
        callback(this);
        if (this.right !== null) {
            this.right.inOrderTraversal(callback);
        }
    }

    /**
     * 前序遍历，用于复制二叉树时，效率最高
     * @param {function} callback 回调函数
     */
    preOrderTraversal(callback) {
        callback(this);
        if (this.left !== null) {
            this.left.preOrderTraversal(callback);
        }
        if (this.right !== null) {
            this.right.preOrderTraversal(callback);
        }
    }

    /**
     * 后序遍历
     * @param {function} callback 回调函数
     */
    postOrderTraversal(callback) {
        if (this.left !== null) {
            this.left.postOrderTraversal(callback);
        }
        if (this.right !== null) {
            this.right.postOrderTraversal(callback);
        }
        callback(this);
    }

    /**
     * 获取最小节点
     * @return {SortedBinaryTree}
     */
    getMin() {
        if (this.left !== null) {
            return this.left.getMin();
        }
        return this;
    }

    /**
     * 获取最大节点
     * @return {SortedBinaryTree}
     */
    getMax() {
        if (this.right !== null) {
            return this.right.getMax();
        }
        return this;
    }

    /**
     * 获取指定key的节点
     * @param {number} key 节点的key
     */
    getNode(key) {
        if (this.key > key) {
            if (this.left !== null) {
                return this.left.getNode(key);
            }
            else {
                return null;
            }
        } else if (this.key < key) {
            if (this.right !== null) {
                return this.right.getNode(key);
            }
            else {
                return null;
            }
        } else {
            return this;
        }
    }

    /**
     * 删除指定key的节点
     * @param {number} key 节点的key
     */
    removeNode(key) {
        if (this.key > key) {
            if (this.left !== null) {
                this.left = this.left.removeNode(key);
                return this;
            }
            else {
                return null;
            }
        } else if (this.key < key) {
            if (this.right !== null) {
                this.right = this.right.removeNode(key);
                return this;
            }
            else {
                return null;
            }
        } else {
            //删除的节点是叶子节点
            if (this.left === null && this.right === null) {
                return null;
            }

            //删除的节点只有右节点，直接赋值给上层
            if (this.left === null) {
                return this.right;
                //删除的节点只有左节点，直接赋值给上层
            } else if (this.right === null) {
                return this.left;
            }

            //删除的节点同时有左右节点，获取节点中右边最小的节点（也可以取左边最大的节点）
            var replaceNode = this.right.getMin(); //this.left.getMax();
            //删除最小节点
            this.removeNode(replaceNode.key);
            //把最小节点的值赋给自己
            this.key = replaceNode.key;
            return this;
        }
    }
}

module.exports = SortedBinaryTree;