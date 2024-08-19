package com.dragomir.fsm.pipeline.step;

public interface Step<I, O> {
    O compute(I input);
}
