import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nuerovent.databinding.LayoutAlertBinding

class AlertAdapter(private val items: List<String>) :
    RecyclerView.Adapter<AlertAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: LayoutAlertBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            LayoutAlertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = items[position]
        
    }

    override fun getItemCount(): Int = items.size
}
