module.exports = class Transition{
    constructor(inputs, outputs){
        this.inputs = inputs;
        this.outputs = outputs;
        this.enabled = false; 
    }
}
