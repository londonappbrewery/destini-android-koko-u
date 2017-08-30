package com.londonappbrewery.destini.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.londonappbrewery.destini.R
import com.londonappbrewery.destini.model.Node
import com.londonappbrewery.destini.model.Story
import com.londonappbrewery.destini.model.story
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity(), AnkoLogger {

    lateinit var currentStory: Node

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentStory = createStory()
        displayStory(currentStory)

        btn_upper.setOnClickListener {
            currentStory = currentStory.upperAnswer.nextStory
            displayStory(currentStory)

            if (currentStory.isEnd) {
                alert("End story") {
                    yesButton { finish() }
                }.show()
            }
        }
        btn_lower.setOnClickListener {
            currentStory = currentStory.lowerAnswer.nextStory
            displayStory(currentStory)

            if (currentStory.isEnd) {
                alert("End story") {
                    yesButton { finish() }
                }.show()
            }

        }
    }

    private fun displayStory(story: Node) {
        if (story.isEnd) {
            tv_story.text = story.description
            btn_upper.visibility = View.INVISIBLE
            btn_lower.visibility = View.INVISIBLE
        } else {
            tv_story.text = currentStory.description
            btn_upper.text = currentStory.upperAnswer.answer
            btn_lower.text = currentStory.lowerAnswer.answer
        }
    }

    private fun createStory(): Story =
            story(description = getString(R.string.T1_Story)) {
                upper(answer = getString(R.string.T1_Ans1)) {
                    story(description = getString(R.string.T3_Story)) {
                        upper(answer = getString(R.string.T3_Ans1)) {
                            end(description = getString(R.string.T6_End))
                        }
                        lower(answer = getString(R.string.T3_Ans2)) {
                            end(description = getString(R.string.T5_End))
                        }
                    }
                }
                lower(answer = getString(R.string.T1_Ans2)) {
                    story(description = getString(R.string.T2_Story)) {
                        upper(answer = getString(R.string.T2_Ans1)) {
                            story(description = getString(R.string.T3_Story)) {
                                upper(answer = getString(R.string.T3_Ans1)) {
                                    end(getString(R.string.T6_End))
                                }
                                lower(answer = getString(R.string.T3_Ans2)) {
                                    end(getString(R.string.T5_End))
                                }
                            }
                        }
                        lower(answer = getString(R.string.T2_Ans2)) {
                            end(description = getString(R.string.T4_End))
                        }
                    }
                }
            }

}
