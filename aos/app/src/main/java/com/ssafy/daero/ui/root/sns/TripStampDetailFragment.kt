package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentTripStampDetailBinding
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class TripStampDetailFragment : BaseFragment<FragmentTripStampDetailBinding>(R.layout.fragment_trip_stamp_detail) {

    private val tripStampDetailViewModel: TripStampDetailViewModel by viewModels()

    private var imageUrl = ""
    private val TAG = "TripStampDetailKakao_Daero"


    override fun init() {
        initData()
        observeData()
        setOnClickListeners()
    }

    private fun initData(){
        val tripStampSeq = arguments?.getInt("tripStampSeq", 0) ?: 0
        tripStampDetailViewModel.getTripStampDetail(tripStampSeq)
    }

    private fun observeData(){
        tripStampDetailViewModel.tripStampDetail.observe(viewLifecycleOwner) {
            binding.textItemTripStampDetailTitle.text = it.place
            binding.textItemTripStampDetailDate.text = it.datetime
            imageUrl = it.image_url

            Glide.with(requireContext())
                .load(it.image_url)
                .placeholder(R.drawable.placeholder_trip_album)
                .apply(RequestOptions().centerCrop())
                .error(R.drawable.placeholder_trip_album)
                .into(binding.imageTripStampDetailStamp)
        }

    }

    private fun setOnClickListeners(){
        binding.apply {
            buttonTripStampDetailShare.setOnClickListener {
                val defaultFeed = FeedTemplate(
                    content = Content(
                        title = binding.textItemTripStampDetailTitle.text.toString(),
                        description = binding.textItemTripStampDetailDate.text.toString(),
                        imageUrl = imageUrl,
                        link = Link(
                            webUrl = "https://developers.kakao.com",
                            mobileWebUrl = "https://developers.kakao.com"
                        )
                    )
                )

                // ???????????? ???????????? ??????
                if (ShareClient.instance.isKakaoTalkSharingAvailable(requireContext())) {
                    // ?????????????????? ???????????? ?????? ??????
                    ShareClient.instance.shareDefault(
                        requireContext(),
                        defaultFeed
                    ) { sharingResult, error ->
                        if (error != null) {
                            Log.e(TAG, "???????????? ?????? ??????", error)
                            toast("???????????? ?????? ??????")
                        } else if (sharingResult != null) {
                            Log.d(TAG, "???????????? ?????? ?????? ${sharingResult.intent}")
                            startActivity(sharingResult.intent)

                            // ???????????? ????????? ??????????????? ?????? ?????? ???????????? ????????? ?????? ?????? ???????????? ?????? ?????? X
                            Log.w(TAG, "Warning Msg: ${sharingResult.warningMsg}")
                            Log.w(TAG, "Argument Msg: ${sharingResult.argumentMsg}")
                        }
                    }
                }
            }

            imgTripStampDetailBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }
}
