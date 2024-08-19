package com.dragomir.fsm.pipeline;

import com.dragomir.fsm.pipeline.step.Step;
import com.dragomir.fsm.state.TransactionState;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.dragomir.fsm.state.TransactionState.WAITING_APPROVAL;

@RequiredArgsConstructor
public class Pipeline<I, O> {
    private final Step<I, O> currentStep;

    public <NewO> Pipeline<I, NewO> andThen(Step<O, NewO> nextStep) {
        return new Pipeline<>(input -> nextStep.compute(currentStep.compute(input)));
    }

    public O execute(I input) {
        return currentStep.compute(input);
    }

    public static <I, O> Pipeline<I, O> of(List<Step> steps) {
        return of(steps, WAITING_APPROVAL);
    }

    public static <I, O> Pipeline<I, O> of(List<Step> steps, TransactionState state) {
        int fromStepIndex = state.ordinal() - TransactionState.APPLIED.ordinal();
        var p = new Pipeline<I, O>(steps.get(fromStepIndex));
        for (int i = fromStepIndex + 1; i < steps.size(); i++) {
            p = p.andThen(steps.get(i));
        }
        return p;
    }
}
