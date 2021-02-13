package com.example.paging_library_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paging_library_example.api.Contents
import com.example.paging_library_example.databinding.ActivityMainBinding
import com.example.paging_library_example.databinding.ItemContentsListBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binder : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val vm = MainViewModel()
        binder.vm = vm

        initAdapter()
    }

    private fun initAdapter(){
        binder.list.adapter = ContentListAdapter()
        binder.list.layoutManager = LinearLayoutManager(this)

        binder.vm?.contentsList?.observe(this, Observer {
            Log.d("godgod", "${it.loadedCount}")
            (binder.list.adapter as ContentListAdapter).submitList(it)
            Log.d("godgod", "complete")
        })
    }
}

class ContentListAdapter : PagedListAdapter<Contents, ContentListAdapter.ContentVH>(diffUtil){

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<Contents>() {
            override fun areItemsTheSame(oldItem: Contents, newItem: Contents): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contents, newItem: Contents): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class ContentVH(private val binder : ItemContentsListBinding) : RecyclerView.ViewHolder(binder.root){
        fun bind(data : Contents?){
            Log.d("godgod", "bind")
            data?.let {
                binder.data = it
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentVH {
        Log.d("godgod", "a")
        val itemBinder = ItemContentsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentVH(itemBinder)
    }

    override fun onBindViewHolder(holder: ContentVH, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

}