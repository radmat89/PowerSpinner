package com.skydoves.powerspinner

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.powerspinner.databinding.ItemDefaultPowerSpinnerLibraryBinding

/** CustomSpinnerAdapter is a copy of DefaultSpinnerAdapter */
class CustomSpinnerAdapter(
  powerSpinnerView: PowerSpinnerView
) : RecyclerView.Adapter<CustomSpinnerAdapter.DefaultSpinnerViewHolder>(),
  PowerSpinnerInterface<CustomSpinnerItemInterface> {

  override var index: Int = powerSpinnerView.selectedIndex
  override val spinnerView: PowerSpinnerView = powerSpinnerView
  override var onSpinnerItemSelectedListener: OnSpinnerItemSelectedListener<CustomSpinnerItemInterface>? = null

  private val spinnerItems: MutableList<CustomSpinnerItemInterface> = arrayListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultSpinnerViewHolder {
    val binding =
      ItemDefaultPowerSpinnerLibraryBinding.inflate(
        LayoutInflater.from(parent.context), parent,
        false
      )
    return DefaultSpinnerViewHolder(binding).apply {
      binding.root.setOnClickListener {
        val position = bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION }
          ?: return@setOnClickListener
        notifyItemSelected(position)
      }
    }
  }

  override fun onBindViewHolder(holder: DefaultSpinnerViewHolder, position: Int) =
    holder.bind(spinnerItems[position].spinnerText, spinnerView)

  override fun setItems(itemList: List<CustomSpinnerItemInterface>) {
    this.spinnerItems.clear()
    this.spinnerItems.addAll(itemList)
    notifyDataSetChanged()
  }

  override fun notifyItemSelected(index: Int) {
    if (index == NO_SELECTED_INDEX) return
    val oldIndex = this.index
    this.index = index
    this.spinnerView.notifyItemSelected(index, spinnerItems[index].spinnerText)
    this.onSpinnerItemSelectedListener?.onItemSelected(
      oldIndex = oldIndex,
      oldItem = oldIndex.takeIf { it != NO_SELECTED_INDEX }?.let { spinnerItems[oldIndex] },
      newIndex = index,
      newItem = spinnerItems[index]
    )
  }

  override fun getItemCount() = spinnerItems.size

  override fun getItems() = this.spinnerItems

  override val selectedItem: CustomSpinnerItemInterface?
    get() {
      return if (index < 0) {
        null
      } else {
        spinnerItems[index]
      }
    }

  override fun selectItem(item: CustomSpinnerItemInterface): Boolean {
    val index = spinnerItems.indexOfFirst { it == item }
    return if (index < 0) {
      false
    } else {
      spinnerView.selectItemByIndex(index)
      true
    }
  }

  class DefaultSpinnerViewHolder(private val binding: ItemDefaultPowerSpinnerLibraryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CharSequence, spinnerView: PowerSpinnerView) {
      binding.itemDefaultText.apply {
        text = item
        typeface = spinnerView.typeface
        gravity = spinnerView.gravity
        setTextSize(TypedValue.COMPLEX_UNIT_PX, spinnerView.textSize)
        setTextColor(spinnerView.currentTextColor)
      }
      binding.root.setPadding(
        spinnerView.paddingLeft,
        spinnerView.paddingTop,
        spinnerView.paddingRight,
        spinnerView.paddingBottom
      )
    }
  }
}
