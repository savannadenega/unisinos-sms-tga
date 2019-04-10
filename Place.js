module.exports = class Place {
    constructor(name, marks, arcs) {
        this.name = name;
        this.marks = marks;
        this.arcs = arcs;
    }

    removeMarks(weight){
        this.marks -= weight; 
    }

    addMarks(weight){
        this.marks += weight;
    }
}

