
class Person {
    constructor(name) {
        this.name = name;
    }

    say() {
        return this.name + ' say hi~';
    }
}

class Female extends Person {
    constructor(name) {
        super(name);
    }

    say() {
        return this.name + ' say, i am female';
    }
}

function start() {
    var female = new Female('Alice');
    console.log(female.__proto__.__proto__.say());
    
    console.log(female.say());
}

start();