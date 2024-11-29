package edu.monash.week9;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.monash.week9.provider.Student;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {

    ArrayList<Student> data = new ArrayList<Student>();

    public void setData(ArrayList<Student> data) {
        this.data = data;
    }

    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CardView inflated as RecyclerView list item
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(v);
        return viewHolder;
    }

    /**
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        // don't forget to convert the integer value to String before setting to TextView
        holder.tvStudentId.setText(String.valueOf(data.get(position).getStudentId()));
        holder.tvIdAuto.setText(data.get(position).getId() + "");
        holder.tvStudentName.setText(data.get(position).getName());
        holder.tvStudentCountry.setText(data.get(position).getStudentCountry());
        if (data.get(position).isRecordActive()) {
            holder.tvIsActive.setText("Active");
        } else {
            holder.tvIsActive.setText("Inactive");
        }

        holder.cardView.setOnClickListener(v -> {
            String selectedStudentCountry = data.get(position).getStudentCountry();

            // TODO: Launch new MapsActivity with Country Name in extras
        });
    }

    @Override
    public int getItemCount() {
        if (this.data != null) { // if data is not null
            return this.data.size(); // then return the size of ArrayList
        }

        // else return zero if data is null
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tvStudentName;
        public TextView tvStudentId;
        public TextView tvIsActive;
        public TextView tvIdAuto;
        public TextView tvStudentCountry;

        public View cardView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView;
            tvStudentId = itemView.findViewById(R.id.tv_id);
            tvStudentName = itemView.findViewById(R.id.tv_name);
            tvIsActive = itemView.findViewById(R.id.tv_active);
            tvIdAuto = itemView.findViewById(R.id.tv_auto_id);
            tvStudentCountry = itemView.findViewById(R.id.tv_country);
        }
    }
}
