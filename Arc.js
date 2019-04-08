module.exports = class Arc{
    constructor(weight = 1, transition, place){
        this.weight = weight;
        this.transition = transition;
        this.place = place;
    }
}

