package com.ssafy.daero.ui.root.sns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.databinding.FragmentArticleMenuBottomSheetBinding
import com.ssafy.daero.ui.root.mypage.ReportListener
import com.ssafy.daero.ui.root.trip.TripFollowBottomSheetFragment
import com.ssafy.daero.utils.constant.ARTICLE
import com.ssafy.daero.utils.constant.ARTICLE_SEQ
import com.ssafy.daero.utils.constant.REPORT_BOTTOM_SHEET


class ArticleMenuBottomSheetFragment(
    private var articleSeq: Int,
    var userSeq: Int,
    var fragmentSeq: Int,
    var listener: ArticleListener,
    var reportListener: ReportListener,
    var expose: Char = 'y',
    var position: Int = 0
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentArticleMenuBottomSheetBinding

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.ArticleMenuBottomSheetFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_article_menu_bottom_sheet,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        init()
    }

    fun init() {
        initView()
        setOnClickListeners()
    }

    private fun initView() {
        if (userSeq == App.prefs.userSeq) {
            binding.tvArticleMenuTripFollow.visibility = View.GONE
            binding.viewArticleMenuTripFollow.visibility = View.GONE
            binding.tvArticleMenuReport.visibility = View.GONE
            binding.viewArticleMenuReport.visibility = View.GONE
            binding.tvArticleMenuBlock.visibility = View.GONE
            binding.tvArticleMenuBlock.visibility = View.GONE
            if (expose == 'y') {
                binding.tvArticleMenuTripGoPublic.text = "????????? ?????????"
            } else {
                binding.tvArticleMenuTripGoPublic.text = "????????? ??????"
            }
        } else {
            binding.tvArticleMenuModify.visibility = View.GONE
            binding.viewArticleMenuModify.visibility = View.GONE
            binding.tvArticleMenuDelete.visibility = View.GONE
            binding.viewArticleMenuDelete.visibility = View.GONE
            binding.tvArticleMenuTripGoPublic.visibility = View.GONE
            binding.viewArticleMenuTripGoPublic.visibility = View.GONE
        }
    }

    private fun setOnClickListeners() {
        binding.tvArticleMenuTripFollow.setOnClickListener {
            // ????????????

            // ?????? ???????????? ?????? ??????
            if (App.prefs.curPlaceSeq > 0) {
                TripFollowBottomSheetFragment(fragmentSeq, articleSeq).show(
                    parentFragmentManager,
                    "TRIP_FOLLOW_BOTTOM_SHEET"
                )
            }
            // ?????? ?????? ????????? ?????? ????????????
            else {
                when (fragmentSeq) {
                    1 -> findNavController().navigate(
                        R.id.action_rootFragment_to_tripFollowSelectFragment,
                        bundleOf(ARTICLE_SEQ to articleSeq)
                    )
                    2 -> findNavController().navigate(
                        R.id.action_articleFragment_to_tripFollowSelectFragment,
                        bundleOf(ARTICLE_SEQ to articleSeq)
                    )
                }
            }
            dismiss()
        }
        binding.tvArticleMenuShare.setOnClickListener {
            //todo: ????????????
        }
        binding.tvArticleMenuModify.setOnClickListener {
            //????????????
            App.isEdit = true
            findNavController().navigate(
                R.id.action_articleFragment_to_articleEditDayFragment,
                bundleOf(ARTICLE_SEQ to articleSeq)
            )
            dismiss()
        }
        binding.tvArticleMenuDelete.setOnClickListener {
            //????????????
            listener.articleDelete(articleSeq)
            dismiss()
        }
        binding.tvArticleMenuReport.setOnClickListener {
            ReportBottomSheetFragment(ARTICLE, articleSeq, reportListener, position).show(
                parentFragmentManager,
                REPORT_BOTTOM_SHEET
            )
            dismiss()
        }
        binding.tvArticleMenuBlock.setOnClickListener {
            //????????????
            listener.blockArticle(articleSeq, position)
            dismiss()
        }
        binding.tvArticleMenuTripGoPublic.setOnClickListener {
            //??????,?????????
            if (expose == 'y') {
                //???????????????
                listener.articleClose(articleSeq)
            } else {
                //????????????
                listener.articleOpen(articleSeq)
            }
            dismiss()
        }
        binding.tvArticleMenuCancel.setOnClickListener {
            dismiss()
        }
    }
}