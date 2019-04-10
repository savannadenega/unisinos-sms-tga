module.exports = class Transition {
    constructor(inputs, outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.enabled = false;
    }

    canFire() {
        let canFire = false;

        inputs.forEach(input => {
            canFire = canFire && input.canFire();
        });

        outputs.forEach(output => {
            canFire = canFire && output.canFire();
        });

        return canFire;
    }
}
