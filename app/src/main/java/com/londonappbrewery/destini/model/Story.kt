package com.londonappbrewery.destini.model

/**
 *
 * Story1 -> Ans1 -> Story3 -> Ans1 -> End6
 *                          -> Ans2 -> End5
 *        -> Ans2 -> Story2 -> Ans1 -> Story3 -> Ans1 -> End6
 *                                            -> Ans2 -> End5
 *                          -> Ans2 -> End4
 *
 *

 * story(description = "...") {
 *     upper(answer = "...") {
 *         story(description = "...") {
 *             upper(answer = "...") {
 *                 end(description = "...")
 *             }
 *             lower(answer = "...") {
 *                 end(description = "...")
 *             }
 *         }
 *     }
 *     lower(answer = "...") {
 *         story {
 *         }
 *     }
 * }
 */

abstract class Node(
        val description: String) {

    open val upperAnswer: Answer get() = emptyAnswer()
    open val lowerAnswer: Answer get() = emptyAnswer()
    abstract val isEnd: Boolean
}


class Story(
        description: String,
        override val upperAnswer: Answer,
        override val lowerAnswer: Answer
) : Node(description) {
    override val isEnd: Boolean get() = false
}

class End(description: String) : Node(description) {
    override val isEnd: Boolean get() = true
}

data class Answer(val answer: String, val nextStory: Node)

fun story(description: String, action: StoryBuilder.() -> Unit): Story {
    val builder = StoryBuilder(description)
    builder.action()
    return builder.build()
}

fun emptyAnswer(): Answer =
        Answer("", End(""))

class StoryBuilder(val description: String) {
    var upperAnswer: Answer = emptyAnswer()
    var lowerAnswer: Answer = emptyAnswer()
    fun upper(answer: String, action: AnswerBuilder.() -> Unit) {
        val builder = AnswerBuilder(answer)
        builder.action()
        upperAnswer = builder.build()
    }
    fun lower(answer: String, action: AnswerBuilder.() -> Unit) {
        val builder = AnswerBuilder(answer)
        builder.action()
        lowerAnswer = builder.build()
    }

    fun build(): Story =
        Story(description, upperAnswer, lowerAnswer)
}
class AnswerBuilder(val answer: String) {
    var nextStory: Node = End("")

    fun story(description: String, action: StoryBuilder.() -> Unit) {
        val builder = StoryBuilder(description)
        builder.action()
        nextStory = builder.build()
    }
    fun end(description: String) {
        nextStory = End(description)
    }

    fun build(): Answer =
        Answer(answer, nextStory)
}

