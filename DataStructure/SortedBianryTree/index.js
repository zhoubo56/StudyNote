var SortedBinaryTree = require('./sorted-binary-tree');

console.log('start ..');

let root = new SortedBinaryTree(8);
let nums = [3, 10, 1, 6, 14, 4, 7, 13, 11, 9, 12, 2, 5];
nums.forEach((k) => {
    root.addChild(new SortedBinaryTree(k));
});

let printKey = function (node) {
    if (node === null) {
        console.log("node is null");
    } else {
        console.log("key is " + node.key);
    }
}

console.log('>>>>inOrderTraversal');
root.inOrderTraversal(printKey);

console.log('>>>>preOrderTraversal');
let root2 = new SortedBinaryTree(root.key);
let insertChild = function (node) {
    printKey(node);
    root2.addChild(node);
}
root.preOrderTraversal(insertChild);
console.log('>>>>root2 inOrderTraversal');
root2.inOrderTraversal(printKey);

console.log('>>>>postOrderTraversal');
root.postOrderTraversal(printKey);

console.log('>>>>getMin');
printKey(root.getMin());

console.log('>>>>getMax');
printKey(root.getMax());

console.log('>>>>getNode');
console.log(root.getNode(10));

console.log('>>>>removeNode');
root.removeNode(11);
root.inOrderTraversal(printKey);

console.log('end ..');