module.exports = class Arc {
    constructor(transition, place, weight = 1) {
        this.transition = transition;
        this.place = place;
        this.weight = weight;
    }

    canFire(){
        return this.place.marks >= this.weight;
    }
}
