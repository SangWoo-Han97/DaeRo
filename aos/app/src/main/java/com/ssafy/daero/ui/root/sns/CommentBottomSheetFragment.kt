package com.ssafy.daero.ui.root.sns

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.data.dto.article.CommentAddRequestDto
import com.ssafy.daero.databinding.BottomsheetCommentBinding
import com.ssafy.daero.ui.adapter.sns.CommentAdapter
import com.ssafy.daero.ui.adapter.sns.ReCommentAdapter
import com.ssafy.daero.ui.root.mypage.ReportListener
import com.ssafy.daero.ui.setting.BlockUserViewModel
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.setFullHeight
import com.ssafy.daero.utils.view.toast

class CommentBottomSheetFragment(private val articleSeq: Int, private val comments: Int, private val userProfileClickListener: (Int) -> Unit) :
    BottomSheetDialogFragment(), CommentListener, ReportListener {

    private val blockUserViewModel: BlockUserViewModel by viewModels()
    private val commentViewModel: CommentViewModel by viewModels()
    private lateinit var commentAdapter: CommentAdapter

    private var _binding: BottomsheetCommentBinding? = null
    private val binding get() = _binding!!

    private val commentItemClickListener: (View, Int, Int, Int, String) -> Unit =
        { _, id, index, userSeq, content ->
            //todo 1. 햄버거 2. 유저 사진
            when (index) {
                1 -> {
                    val commentMenuBottomSheetFragment = CommentMenuBottomSheetFragment(
                        id,
                        content,
                        userSeq,
                        this@CommentBottomSheetFragment,
                        this@CommentBottomSheetFragment
                    )
                    commentMenuBottomSheetFragment.show(
                        childFragmentManager,
                        commentMenuBottomSheetFragment.tag
                    )
                }
                2 -> {
                    userProfileClickListener(id)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return setFullHeight()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        getComment()
        observeData()
        setOnClickListeners()
    }

    private fun initView() {
        binding.textCommentCount.text = "$comments"
    }

    private fun getComment() {
        commentViewModel.commentSelect(articleSeq)
    }

    private fun observeData() {
        commentViewModel.comment.observe(viewLifecycleOwner) {
            commentAdapter = CommentAdapter(articleSeq, this@CommentBottomSheetFragment).apply {
                this.onItemClickListener = commentItemClickListener
            }
            commentAdapter.submitData(lifecycle, it)
            binding.recyclerComment.apply {
                adapter = commentAdapter
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            }
        }
    }

    override fun commentUpdate(content: String, sequence: Int) {
        binding.editTextCommentAddComment.requestFocus()
        binding.editTextCommentAddComment.postDelayed({
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(
                binding.editTextCommentAddComment,
                InputMethodManager.SHOW_FORCED
            )
        }, 150)
        binding.editTextCommentAddComment.setText(content)
        binding.textCommentWrite.setOnClickListener {
            commentViewModel.commentUpdate(
                sequence,
                CommentAddRequestDto(binding.editTextCommentAddComment.text.toString())
            )
            binding.editTextCommentAddComment.postDelayed({
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    binding.editTextCommentAddComment.windowToken,
                    0
                )
            }, 0)
            binding.editTextCommentAddComment.setText("")
            toast("댓글이 수정되었습니다.")
            commentAdapter.refresh()
        }
    }

    override fun commentDelete(sequence: Int) {
        commentViewModel.commentDelete(sequence)
        commentAdapter.refresh()
    }

    override fun reCommentAdd(sequence: Int) {
        binding.editTextCommentAddComment.requestFocus()
        binding.editTextCommentAddComment.postDelayed({
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(
                binding.editTextCommentAddComment,
                InputMethodManager.SHOW_FORCED
            )
        }, 150)
        binding.textCommentWrite.setOnClickListener {
            commentViewModel.reCommentAdd(
                articleSeq,
                sequence,
                CommentAddRequestDto(binding.editTextCommentAddComment.text.toString())
            )
            binding.editTextCommentAddComment.postDelayed({
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    binding.editTextCommentAddComment.windowToken,
                    0
                )
            }, 0)
            binding.editTextCommentAddComment.setText("")
            toast("답글이 추가되었습니다.")
            commentAdapter.refresh()
        }
    }

    private fun setOnClickListeners() {
        binding.imageCommentClose.setOnClickListener { dismiss() }
        binding.textCommentWrite.setOnClickListener {
            commentViewModel.commentAdd(
                articleSeq,
                CommentAddRequestDto(binding.editTextCommentAddComment.text.toString())
            )
            binding.editTextCommentAddComment.setText("")
            binding.editTextCommentAddComment.postDelayed({
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    binding.editTextCommentAddComment.windowToken,
                    0
                )
            }, 0)
            toast("댓글이 추가되었습니다.")
            commentAdapter.refresh()
        }
    }

    override fun reCommentSelect(
        articleSeq: Int,
        replySeq: Int,
        reCommentAdapter: ReCommentAdapter
    ): ReCommentAdapter {
        commentViewModel.reCommentSelect(articleSeq, replySeq)
        commentViewModel.reComment.observe(viewLifecycleOwner) {
            reCommentAdapter.submitData(lifecycle, it)
        }.run {
            return reCommentAdapter
        }
    }

    override fun blockAdd(userSeq: Int) {
        blockUserViewModel.blockAdd(userSeq)
        blockUserViewModel.blockState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    toast("해당 유저를 차단했습니다.")
                    commentAdapter.refresh()
                    blockUserViewModel.blockState.value = DEFAULT
                }
                FAIL -> {
                    toast("유저 차단을 실패했습니다.")
                    blockUserViewModel.blockState.value = DEFAULT
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun block(seq: Int, position: Int) {
        blockUserViewModel.blockAdd(seq)
        blockUserViewModel.blockState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    commentAdapter.refresh()
                    blockUserViewModel.blockState.value = DEFAULT
                }
                FAIL -> {
                    blockUserViewModel.blockState.value = DEFAULT
                }
            }
        }
    }
}


