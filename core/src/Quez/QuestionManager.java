package Quez;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Utilities.MathHelper;

public abstract class QuestionManager {

    static Random rand = new Random();

    public static Question createQuestion(QuestionSetting setting) {
        int diff = setting.diff;
        MathOperation operation = setting.operation;
        Question ques = new Question();
        ques.operation = operation;

        if (ques.operation == MathOperation.Mixed) {
            ques.operation = MathOperation.values()[MathHelper.randInt(0, 4)];
        }
        if (ques.operation == MathOperation.Addition) {
            int max = MathHelper.randInt(diff * 6, diff * 11);
            int n = MathHelper.randInt(0, max);
            int t = max - n;
            ques.left = t;
            ques.right = n;
            ques.correctQuestion = ques.left + ques.right;
        } else if (ques.operation == MathOperation.Subtraction) {
            int max = MathHelper.randInt(diff * 6, diff * 11);
            int n = MathHelper.randInt(0, max);
            int t = max - n;
            ques.left = t;
            ques.right = n;
            if (ques.left < ques.right) {
                int zt = ques.left;
                ques.left = ques.right;
                ques.right = zt;
            }
            ques.correctQuestion = ques.left - ques.right;
        } else if (ques.operation == MathOperation.Multiplication) {
            int f = 0;
            int l = 0;
            while (f * l <= diff * 5 || f * l >= diff * 21) {
                f = Math.abs(rand.nextInt()) % (diff + 8);
                l = Math.abs(rand.nextInt()) % (diff + 8);
            }
            ques.left = f;
            ques.right = l;
            ques.correctQuestion = ques.left * ques.right;
        } else if (ques.operation == MathOperation.Division) {
            int f = 0;
            int l = 0;
            while (f * l <= diff * 5 || f * l >= diff * 21) {
                f = Math.abs(rand.nextInt()) % (diff + 8);
                l = Math.abs(rand.nextInt()) % (diff + 8);
            }
            int x = f * l;
            ques.left = x;
            ques.right = l;
            ques.correctQuestion = ques.left / ques.right;
        }

        ques.randomAnswers = new int[3];

        List<Integer> solution = new ArrayList<Integer>();
        {
            solution.add(ques.correctQuestion);
            solution.add(ques.correctQuestion + MathHelper.randInt(5, 10));
            solution.add(Math.abs(ques.correctQuestion - MathHelper.randInt(1, 5)));
        }
        Collections.shuffle(solution);

        for (int i = 0; i < ques.randomAnswers.length; i++) {
            ques.randomAnswers[i] = solution.get(i);
        }

        return ques;
    }
}
