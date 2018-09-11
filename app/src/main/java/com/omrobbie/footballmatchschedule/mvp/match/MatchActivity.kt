package com.omrobbie.footballmatchschedule.mvp.match

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.omrobbie.footballmatchschedule.R
import com.omrobbie.footballmatchschedule.model.LeagueResponse
import com.omrobbie.footballmatchschedule.model.LeaguesItem
import com.omrobbie.footballmatchschedule.utils.invisible
import com.omrobbie.footballmatchschedule.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MatchActivity : AppCompatActivity(), MatchView {

    lateinit var presenter: MatchPresenter

    lateinit var spinner: Spinner
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupLayout()
        setupEnv()
    }

    override fun showLoading() {
        progressBar.visible()
        recyclerView.invisible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        recyclerView.visible()
    }

    override fun showLeagueList(data: LeagueResponse) {
        spinner.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, data.leagues)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = spinner.selectedItem as LeaguesItem
                toast(item.idLeague.toString())
            }
        }
    }

    fun setupLayout() {
        linearLayout {
            orientation = LinearLayout.VERTICAL

            linearLayout {
                orientation = LinearLayout.VERTICAL
                backgroundColor = Color.LTGRAY

                spinner = spinner {
                    padding = dip(16)
                    minimumHeight = dip(80)
                }
            }

            relativeLayout {
                recyclerView = recyclerView {
                    layoutManager = LinearLayoutManager(ctx)
                }.lparams(matchParent, matchParent)

                progressBar = progressBar {
                }.lparams {
                    centerInParent()
                }

                bottomNavigationView {
                    backgroundColor = Color.WHITE

                    menu.apply {
                        add("Prev. Match")
                                .setIcon(R.drawable.ic_trophy)
                                .setOnMenuItemClickListener {
                                    toast("prev")
                                    false
                                }

                        add("Next Match")
                                .setIcon(R.drawable.ic_event)
                                .setOnMenuItemClickListener {
                                    toast("next")
                                    false
                                }
                    }
                }.lparams(matchParent, wrapContent) {
                    alignParentBottom()
                }
            }.lparams(matchParent, matchParent)
        }
    }

    fun setupEnv() {
        presenter = MatchPresenter(this)

        presenter.getLeagueAll()
    }
}