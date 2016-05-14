package com.brunsting.jacob.mobilemathclass;

public class Quiz {
    private int type;
    private int difficulty;
    private int numQuestions;
    private String[] questionKey;
    private String[] answerKey;

    // Difficulty Preferences
    private static final int[][] NUM_TERMS_BY_DIFFICULTY =
            {{2, 3, 5, 7},              // Arithmetic
             {2, 2, 3, 4},              // Multiplication
             {2, 2, 3, 4},              // Adding and Subtracting Fractions
             {2, 2, 3, 4},              // Multiplying and Dividing Fractions
             {2, 3, 4, 6},              // Basic Algebra
             {2, 3, 4, 6},              // Algebra with Multiplication
             {2, 2, 3, 3}};             // Algebra with Fractions
    private static final int[][] MAX_NUM_BY_DIFFICULTY =
            {{12, 25, 50, 150},         // Arithmetic
             {6, 12, 15, 25},           // Multiplication
             {6, 8, 10, 25},            // Adding and Subtracting Fractions
             {5, 7, 10, 20},            // Multiplying and Dividing Fractions
             {10, 20, 40, 120},         // Basic Algebra
             {6, 12, 15, 25},           // Algebra with Multiplication
             {4, 6, 10, 25}};           // Algebra with Fractions
    private static final int[][] TERM_DEVIATION_BY_DIFFICULTY =
            {{0, 1, 1, 2},              // Arithmetic
             {0, 0, 1, 1},              // Multiplication
             {0, 0, 1, 1},              // Adding and Subtracting Fractions
             {0, 0, 1, 1},              // Multiplying and Dividing Fractions
             {0, 1, 1, 2},              // Basic Algebra
             {0, 1, 1, 2},              // Algebra with Multiplication
             {0, 0, 1, 1}};             // Algebra with Fractions


    // Constructor
    public Quiz(int qType, int qDifficulty, int qNumQuestions) {
        type = qType;
        difficulty = qDifficulty;
        numQuestions = qNumQuestions;
        questionKey = new String[qNumQuestions];
        answerKey = new String[qNumQuestions];

        switch(type) {
            case 1:
                // Create an arithmetic question
                createArithmeticQuestions();
                break;
            case 2:
                // Create a multiplication question
                createMultiplicationQuestions();
                break;
            case 3:
                // Create a adding and subtracting fractions question
                createAddSubFractionsQuestions();
                break;
            case 4:
                // Create a multiplication and division of fractions question
                createMultDivFractionsQuestions();
                break;
            case 5:
                // Create an algebra question without an x multiple with a type of 4 (so it gets the correct difficulty level information)
                createAlgebraQuestions(4, false);
                break;
            case 6:
                // Create an algebra question with an x multiple, with a type of 5
                createAlgebraQuestions(5, true);
                break;
            case 7:
                // Create an algebra question with fractions
                createDivisionAlgebraQuestions();
                break;
        }
    }

    // createArithmeticQuestions() creates a set number of addition/subtraction question
    private void createArithmeticQuestions() {
        // Set the parameters based on the question type and difficulty
        final int numTerms = NUM_TERMS_BY_DIFFICULTY[0][difficulty];
        final int maxNum = MAX_NUM_BY_DIFFICULTY[0][difficulty];
        final int termDeviation = TERM_DEVIATION_BY_DIFFICULTY[0][difficulty];

        // Generate numQuestions questions
        for (int q = 0; q < numQuestions; q++) {
            final int currentTermDeviation = (int)Math.floor(termDeviation * (Math.random() + 0.5));
            final int currentNumTerms = numTerms - currentTermDeviation;
            String currentQuestion = "";
            int currentNumericalAnswer = 0;

            // Generate a question with currentNumTerms terms
            for (int t = 0; t < currentNumTerms; t++) {
                // Generate a random term
                final int term = createRandomTerm(1, maxNum);
                final boolean positive = (Math.random() < 0.5 || difficulty == 0);

                // Insert the term into the question
                if (positive) {
                    if (t == 0) {
                        currentQuestion = Integer.toString(term);
                    } else {
                        currentQuestion = currentQuestion + " + " + term;
                    }
                    currentNumericalAnswer = currentNumericalAnswer + term;
                } else {
                    currentQuestion = currentQuestion + " - " + term;
                    currentNumericalAnswer = currentNumericalAnswer - term;
                }
            }
            questionKey[q] = currentQuestion;
            answerKey[q] = Integer.toString(currentNumericalAnswer);
        }
    }

    // createMultiplicationQuestions() creates a set number of multiplication questions
    private void createMultiplicationQuestions() {
        // Set the parameters based on the question type and difficulty
        final int numTerms = NUM_TERMS_BY_DIFFICULTY[1][difficulty];
        final int maxNum = MAX_NUM_BY_DIFFICULTY[1][difficulty];
        final int termDeviation = TERM_DEVIATION_BY_DIFFICULTY[1][difficulty];

        // Generate numQuestions questions
        for (int q = 0; q < numQuestions; q++) {
            // Set the initial values required for term generation
            final int currentTermDeviation = (int)Math.floor(termDeviation * (Math.random() + 0.5));
            final int currentNumTerms = numTerms - currentTermDeviation;
            String currentQuestion = "";
            int currentNumericalAnswer = 1;

            // Generate a question with currentNumTerms terms
            for (int t = 0; t < currentNumTerms; t++) {
                // Generate the term
                final int term = createRandomTerm(1, maxNum);
                final boolean positive = (Math.random() < 0.5 || difficulty == 0);

                // Insert the term into the question
                if (t == 0) {
                    if (positive) {
                        currentQuestion = Integer.toString(term);
                        currentNumericalAnswer = term;
                    } else {
                        currentQuestion = "-" + Integer.toString(term);
                        currentNumericalAnswer = -term;
                    }
                } else {
                    if (positive) {
                        currentQuestion = currentQuestion + " × " + term;
                        currentNumericalAnswer = currentNumericalAnswer * term;
                    } else {
                        currentQuestion = currentQuestion + " × -" + term;
                        currentNumericalAnswer = currentNumericalAnswer * term * -1;
                    }
                }
            }

            // Save the question
            questionKey[q] = currentQuestion;
            answerKey[q] = Integer.toString(currentNumericalAnswer);
        }
    }

    // createAddSubFractionsQuestions() creates a set number of questions about adding and
    //    subtracting fractions
    private void createAddSubFractionsQuestions() {
        // Set the initial values based on the difficulty level and quiz type
        final int numTerms = NUM_TERMS_BY_DIFFICULTY[2][difficulty];
        final int maxNum = MAX_NUM_BY_DIFFICULTY[2][difficulty];
        final int termDeviation = TERM_DEVIATION_BY_DIFFICULTY[2][difficulty];

        // Generate numQuestions questions
        for (int q = 0; q < numQuestions; q++){
            final int currentTermDeviation = (int)Math.floor(termDeviation * (Math.random() + 0.5));
            final int currentNumTerms = numTerms - currentTermDeviation;
            String currentQuestion = "";
            int currentAnswerNumerator = 0;
            int currentAnswerDenominator = 1;

            // Generate a question with currentNumTerms terms
            for (int t = 0; t < currentNumTerms; t++){
                // Generate a random fraction
                final int numerator = createRandomTerm(1, maxNum);
                final int denominator = createRandomTerm(1, maxNum);

                // Add 50% of the time, or always if the difficulty level is 0
                if (Math.random() < 0.5 || difficulty == 0){
                    // Add to the answer
                    currentAnswerNumerator = (currentAnswerNumerator * denominator) + (currentAnswerDenominator * numerator);
                    currentAnswerDenominator = currentAnswerDenominator * denominator;

                    // Do not add a "+" sign if it is the first term
                    if (t == 0) {
                        currentQuestion = currentQuestion + Integer.toString(numerator) + "/" + Integer.toString(denominator);
                    } else {
                        currentQuestion = currentQuestion + " + " + Integer.toString(numerator) + "/" + Integer.toString(denominator);
                    }
                } else {
                    // Subtract from the answer and add on to the question
                    currentAnswerNumerator = (currentAnswerNumerator * denominator) - (currentAnswerDenominator * numerator);
                    currentAnswerDenominator = currentAnswerDenominator * denominator;
                    currentQuestion = currentQuestion + " - " + Integer.toString(numerator) + "/" + Integer.toString(denominator);
                }
            }

            // Make sure the answer denominator is positive
            if (currentAnswerDenominator < 0) {
                currentAnswerDenominator *= -1;
                currentAnswerNumerator *= -1;
            }

            // Find the GCD to help simplify the answer
            int gcdNumDen = findGcd(currentAnswerNumerator, currentAnswerDenominator);

            // Avoid any divide by 0 errors
            if (gcdNumDen == 0) {
                gcdNumDen = 1;
            }

            // Divide the numerator and denominator by the GCD to put in lowest terms
            currentAnswerDenominator = currentAnswerDenominator / gcdNumDen;
            currentAnswerNumerator = currentAnswerNumerator / gcdNumDen;

            // Save the question and answer
            questionKey[q] = currentQuestion;
            if (currentAnswerDenominator == 1) {
                answerKey[q] = Integer.toString(currentAnswerNumerator);
            } else {
                answerKey[q] = Integer.toString(currentAnswerNumerator) + "/"
                        + Integer.toString(currentAnswerDenominator);
            }
        }
    }

    // createMultDivFractionsQuestions() creates a set number of questions that tests the users
    //    ability to multiply and divide fractions
    private void createMultDivFractionsQuestions() {
        // Set the parameters based on the quiz type and difficulty level
        final int numTerms = NUM_TERMS_BY_DIFFICULTY[3][difficulty];
        final int maxNum = MAX_NUM_BY_DIFFICULTY[3][difficulty];
        final int termDeviation = TERM_DEVIATION_BY_DIFFICULTY[3][difficulty];

        // Generate numQuestions questions
        for (int q = 0; q < numQuestions; q++){
            // Set the initial values of the variables required for question generation
            final int currentTermDeviation = (int)Math.floor(termDeviation * (Math.random() + 0.5));
            final int currentNumTerms = numTerms - currentTermDeviation;
            String currentQuestion = "";
            int currentAnswerNumerator = 1;
            int currentAnswerDenominator = 1;

            // Generate a question with currentNumTerms terms
            for (int t = 0; t < currentNumTerms; t++) {
                // Generate a random fraction
                final int numerator = createRandomTerm(1, maxNum);
                final int denominator = createRandomTerm(1, maxNum);

                // If it is the first term, the answer is the question, otherwise, multiply the answer by the new fraction
                if (t == 0) {
                    currentAnswerNumerator = numerator;
                    currentAnswerDenominator = denominator;
                    currentQuestion = Integer.toString(numerator) + "/" + Integer.toString(denominator);
                } else {
                    // Multiply the fractions 70% of the time, or if the difficulty level is 0
                    if (Math.random() < 0.7 || difficulty == 0) {
                        // Multiply the answer, and add to the question
                        currentAnswerNumerator = currentAnswerNumerator * numerator;
                        currentAnswerDenominator = currentAnswerDenominator * denominator;
                        currentQuestion = currentQuestion + " × " + Integer.toString(numerator) + "/" + Integer.toString(denominator);
                    } else {
                        // Multiply the reciprocal of the new term by the question, and add to the question
                        currentAnswerNumerator = currentAnswerNumerator * denominator;
                        currentAnswerDenominator = currentAnswerDenominator * numerator;
                        currentQuestion = currentQuestion + " ÷ " + Integer.toString(numerator) + "/" + Integer.toString(denominator);
                    }
                }
            }

            // Make sure the denominator is positive
            if (currentAnswerDenominator < 0) {
                currentAnswerDenominator *= -1;
                currentAnswerNumerator *= -1;
            }

            // Find the GCD to help with simplifying the answer
            int gcdNumDen = findGcd(currentAnswerNumerator, currentAnswerDenominator);

            // Avoid any divide by 0 errors
            if (gcdNumDen == 0) {
                gcdNumDen = 1;
            }

            // Simplify the answer
            currentAnswerDenominator = currentAnswerDenominator / gcdNumDen;
            currentAnswerNumerator = currentAnswerNumerator / gcdNumDen;

            // Set the question and answer
            questionKey[q] = currentQuestion;
            if (currentAnswerDenominator == 1) {
                answerKey[q] = Integer.toString(currentAnswerNumerator);
            } else if (currentAnswerNumerator == 0) {
                answerKey[q] = "0";
            } else {
                answerKey[q] = Integer.toString(currentAnswerNumerator) + "/"
                        + Integer.toString(currentAnswerDenominator);
            }
        }
    }

    // createAlgebraQuestions(typeNumber, randomizeXMultiple) creates a set number of algebra
    //   questions of type typeNumber, where typeNumber is the number that is used to reference
    //   the difficulty information for the question, and randomizeXMultiple determines whether
    //   x should be on its own, or if it should be multiplied by a random number
    private void createAlgebraQuestions(int typeNumber, boolean randomizeXMultiple) {
        // Set the initial values required for question generation
        final int numTerms = NUM_TERMS_BY_DIFFICULTY[typeNumber][difficulty];
        final int maxNum = MAX_NUM_BY_DIFFICULTY[typeNumber][difficulty];
        final int termDeviation = TERM_DEVIATION_BY_DIFFICULTY[typeNumber][difficulty];

        // Generate numQuestions questions
        for (int q = 0; q < numQuestions; q++) {
            // Set the initial values required for the individual question generation
            final int currentTermDeviation = (int) Math.floor(termDeviation * (Math.random() + 0.5));
            String currentQuestion = " =";
            int currentNumericalAnswer = 0;
            int currentNumTerms = numTerms - currentTermDeviation;
            if (currentNumTerms <= 0) {
                currentNumTerms = 1;
            }
            boolean xNegative = false;
            boolean xOnRight = false;
            boolean xInserted = false;
            int xMultiple;

            // Pick a random x multiple if the question is supposed to have an x multiple
            if (randomizeXMultiple) {
                xMultiple = createRandomTerm(1, maxNum);
            } else {
                xMultiple = 1;
            }

            // Generate a question with currentNumTerms terms
            for (int t = 0; t < currentNumTerms; t++) {
                // Get a random term
                int term = createRandomTerm(1, maxNum);

                // Insert the variable into the question if the random number generator tells it to, or if the last
                //   term is being generated, as long as the variable has not yet been inserted
                if (!xInserted && (Math.random() < 1 / currentNumTerms || t + 1 == currentNumTerms)) {
                    // Insert the variable, and store its position and value
                    xNegative = (Math.random() > 0.5) && difficulty != 0;
                    xOnRight = (Math.random() > 0.5);
                    boolean removeSign = t == 0 && xOnRight && !xNegative;
                    xInserted = true;
                    currentQuestion = insertTermInQuestion("x", xNegative, xOnRight, removeSign, xMultiple, 1, currentQuestion);
                }

                // Insert the term into the question
                final boolean negative = ((Math.random() > 0.5) && difficulty != 0);
                final boolean onRight = ((Math.random() > 0.5) || t == 0);
                final boolean removeSign = t == 0 && (!xInserted || !xOnRight) && !negative;
                currentQuestion = insertTermInQuestion(Integer.toString(term), negative, onRight, removeSign, 1, 1, currentQuestion);

                // Add or subtract from the numerical answer
                if (negative && !onRight || !negative && onRight) {
                    currentNumericalAnswer += term;
                } else {
                    currentNumericalAnswer -= term;
                }
            }

            // Invert the answer if necessary
            if (xNegative && !xOnRight || !xNegative && xOnRight) {
                currentNumericalAnswer *= -1;
            }

            // Save the question and answer
            questionKey[q] = cleanQuestion(currentQuestion);
            answerKey[q] = generateAnswer(currentNumericalAnswer, xMultiple);
        }
    }

    // createDivisionAllgebraQuestions() creates a set number of questions which consists of
    //    fractions with a variable in one of the fractions
    private void createDivisionAlgebraQuestions() {
        // Store the preferences based on the quiz type and difficulty level
        final int numTerms = NUM_TERMS_BY_DIFFICULTY[6][difficulty];
        final int maxNum = MAX_NUM_BY_DIFFICULTY[6][difficulty];
        final int termDeviation = TERM_DEVIATION_BY_DIFFICULTY[6][difficulty];

        // Generate numQuestions questions
        for (int q = 0; q < numQuestions; q++) {
            // Set the initial values for all of the variables required to generate a question
            final int currentTermDeviation = (int)Math.floor(termDeviation * (Math.random() + 0.5));
            String currentQuestion = " =";
            int currentAnswerDenominator = 1;
            int currentAnswerNumerator = 0;
            int currentNumTerms = numTerms - currentTermDeviation;
            if (currentNumTerms <= 0) {
                currentNumTerms = 1;
            }
            boolean xNegative = false;
            boolean xOnRight = false;
            boolean xInserted = false;
            final int xMultiple = createRandomTerm(1, maxNum);
            final int xDivisor = createRandomTerm(1, maxNum);

            // Generate a question with currentNumTerms terms
            for (int t = 0; t < currentNumTerms; t++) {
                // Make a random fraction
                final int numerator = createRandomTerm(1, maxNum);
                final int denominator = createRandomTerm(1, maxNum);

                // Insert the variable into the question if the random number generator tells it to, or if the last
                //   term is being generated, as long as the variable has not yet been inserted
                if (!xInserted && (Math.random() < 1 / currentNumTerms || t + 1 == currentNumTerms))  {
                    // Generate the term and insert it into the question, while saving the position of the variable
                    xNegative = (Math.random() > 0.5) && difficulty != 0;
                    xOnRight = (Math.random() > 0.5);
                    boolean removeSign = t == 0 && xOnRight && !xNegative;
                    xInserted = true;
                    currentQuestion = insertTermInQuestion("x", xNegative, xOnRight, removeSign, xMultiple, xDivisor, currentQuestion);
                }

                //Add on to the question
                final boolean negative = (Math.random() > 0.5) && difficulty != 0;
                final boolean onRight = (Math.random() > 0.5) || t == 0;
                final boolean removeSign = t == 0 && (!xInserted || !xOnRight) && !negative;
                String currentTerm = Integer.toString(numerator) + "/" + Integer.toString(denominator);
                currentQuestion = insertTermInQuestion(currentTerm, negative, onRight, removeSign, 1, 1, currentQuestion);

                // Adjust the question answer by adding or subtracting the new fraction from the question
                if (negative && !onRight || !negative && onRight) {
                    currentAnswerNumerator = denominator * currentAnswerNumerator + numerator * currentAnswerDenominator;
                } else {
                    currentAnswerNumerator = denominator * currentAnswerNumerator - numerator * currentAnswerDenominator;
                }
                currentAnswerDenominator *= denominator;
            }

            // Isolate the variable so that it has no multiples or divisors
            currentAnswerNumerator *= xDivisor;
            currentAnswerDenominator *= xMultiple;

            // Invert the answer if necessary
            if (xNegative && !xOnRight || !xNegative && xOnRight) {
                currentAnswerNumerator *= -1;
            }

            // Save the question and answer
            questionKey[q] = cleanQuestion(currentQuestion);
            answerKey[q] = generateAnswer(currentAnswerNumerator, currentAnswerDenominator);
        }
    }

    // generateAnswer(ansNumerator, ansDenominator) simplifies the question answer with
    //   numerator and denominator anssNumerator, ansDenominator, and adds an "x = " to the start
    //   of the answer, which is required for algebra questions
    private String generateAnswer(int ansNumerator, int ansDenominator){
        // Store the arguments as variables
        int numerator = ansNumerator;
        int denominator = ansDenominator;

        // Find the gcd of the numerator and denominator to help with simplifying them
        int gcdNumDen = findGcd(numerator, denominator);

        // Ensure there are no divide by 0 errors
        if (gcdNumDen == 0) {
            gcdNumDen = 1;
        }

        // Ensure the numerator is positive
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }

        // Divide both sides by the GCD to put them in lowest terms
        numerator /= gcdNumDen;
        denominator /= gcdNumDen;

        // Return the result
        if (denominator == 1) {
            return "x = " + Integer.toString(numerator);
        } else if (numerator == 0) {
            return "x = 0";
        } else {
            return "x = " + Integer.toString(numerator) + "/" + Integer.toString(denominator);
        }
    }

    // insertTermInQuestion(term, negative, onRight, removeSign, multiple, divisor, currentQuestion)
    //    inserts a term, term, in the question currentQuestion, determining if it should be negative
    //   with negative, determines what side it should be inserted on with onRight, determines
    //   if the sign should be removed (true if the term is being put after an '=' or at the start
    //   of the question) with removeSign, and determines the multiple and divisor for the term with
    //   multiple and divisor
    private String insertTermInQuestion(String term, boolean negative, boolean onRight, boolean removeSign, int multiple, int divisor, String currentQuestion) {
        //Create the term that must be added to the question
        String currentTerm = term;
        if (multiple != 1) {
            currentTerm = multiple + currentTerm;
        }
        currentTerm = " " + currentTerm;

        // Add a division symbol of the divisor is not 1
        if (divisor != 1) {
            currentTerm += "/" + divisor;
        }

        // Ad a negative symbol of the term is negative
        if (negative) {
            currentTerm = " -" + currentTerm;
        } else if (!removeSign) {
            currentTerm = " +" + currentTerm;
        }

        //Insert the term into the question
        if (onRight) {
            return currentQuestion +  currentTerm;
        } else {
            return currentTerm + currentQuestion;
        }
    }

    // cleanQuestion(question) takes a question, and inserts and removes the appropriate characters
    //   from question so that the question has a number before the '=', and has no leading '+' signs
    private String cleanQuestion(String question) {
        String newQuestion = question;
        if (question.charAt(1) == '=') {
            newQuestion = "0" + question;
        } else if (question.charAt(1) == '+') {
            newQuestion = question.substring(2);
        }
        return newQuestion;
    }

    // createRandomTerm() generates a random integer between minNumber and maxNumber
    private int createRandomTerm(int minNumber, int maxNumber){
        return (int) (Math.ceil((maxNumber - minNumber) * Math.random())) + minNumber;
    }

    // findGCD(num1, num2) uses a modified version of the Euclidean Algorithm to determine the
    //   GCD of num1 and num2
    private int findGcd(int num1, int num2) {
        num1 = Math.abs(num1);
        num2 = Math.abs(num2);

        // Make sure num1 > num2
        if (num1 < num2) {
            int x = num1;
            num1 = num2;
            num2 = x;
        }

        // Return 0 if num2 is 0
        if (num2 == 0) {
            return 0;
        }

        // Generate the next number based on the Euclidean Algorithm
        final int newNumber = num1 - ((int) Math.floor(num1 / num2) * num2);
        final int nextStep = findGcd(num2, newNumber);

        // Return num2 if the next step returns 0, otherwise return the value of the next step, following the Euclidean Algorithm
        if (nextStep == 0) {
            return num2;
        } else {
            return nextStep;
        }
    }

    // getNumQuestions() returns the number of questions in the quiz
    public int getNumQuestions(){
        return numQuestions;
    }

    // getQuestionKey() returns a table of all of the questions in the quiz
    public String[] getQuestionKey() {
        return questionKey;
    }

    // getAnswerKey() returns a table of all of the answers in the quiz
    public String[] getAnswerKey(){
        return answerKey;
    }
}
