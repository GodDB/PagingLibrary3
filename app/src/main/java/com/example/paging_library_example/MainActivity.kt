package com.example.paging_library_example

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paging_library_example.api.Contents
import com.example.paging_library_example.databinding.ActivityMainBinding
import com.example.paging_library_example.databinding.ItemContentsListBinding
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private lateinit var binder: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val vm = MainViewModel()
        binder.vm = vm

        initAdapter()
    }

    private fun initAdapter() {
        binder.list.adapter = ContentListAdapter().apply {
            // paging Adapter가 paging의 로딩값을 가지고 있다..
            addLoadStateListener {

                //로딩 처리
                if(it.append is LoadState.Error)
                    Toast.makeText(this@MainActivity, "에러발생", Toast.LENGTH_SHORT).show()
            }
        }
        binder.list.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launchWhenResumed {
            binder.vm?.pagingData?.collect {
                (binder.list.adapter as ContentListAdapter).submitData(it)
            }
        }

    }
}

class ContentListAdapter : PagingDataAdapter<Contents, ContentListAdapter.ContentVH>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Contents>() {
            override fun areItemsTheSame(oldItem: Contents, newItem: Contents): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contents, newItem: Contents): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class ContentVH(private val binder: ItemContentsListBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun bind(data: Contents?) {
            data?.let {
                binder.data = it
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentVH {
        val itemBinder =
            ItemContentsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentVH(itemBinder)
    }

    override fun onBindViewHolder(holder: ContentVH, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

}