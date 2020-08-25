package com.example.duty.team;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.duty.R;
import com.example.duty.team.data.DateInfo;
import com.example.duty.team.data.MemberInfo;
import com.example.duty.team.data.ShiftInfo;
import com.kelin.scrollablepanel.library.PanelAdapter;

import java.util.ArrayList;
import java.util.List;



public class TeamCalendarAdapter extends PanelAdapter {
    private static final int TITLE_TYPE = 4;
    private static final int MEMBER_TYPE = 0;
    private static final int DATE_TYPE = 1;
    private static final int SHIFT_TYPE = 2;

    private List<MemberInfo> memberInfoList =new ArrayList<>();
    private List<DateInfo> dateInfoList = new ArrayList<>();
    private List<List<ShiftInfo>> shiftsList =new ArrayList<>();


    @Override
    public int getRowCount() {
        return memberInfoList.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return dateInfoList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        int viewType = getItemViewType(row, column);
        switch (viewType) {
            case DATE_TYPE:
                setDateView(column, (DateViewHolder) holder);
                break;
            case MEMBER_TYPE:
                setMemberView(row, (MemberViewHolder) holder);
                break;
            case SHIFT_TYPE:
                setShiftView(row, column, (ShiftViewHolder) holder);
                break;
            case TITLE_TYPE:
                break;
                /*
            default:
                setOrderView(row, column, (OrderViewHolder) holder);
                 */
        }
    }

    public int getItemViewType(int row, int column) {
        if (column == 0 && row == 0) {
            return TITLE_TYPE;
        }
        if (column == 0) {
            return MEMBER_TYPE;
        }
        if (row == 0) {
            return DATE_TYPE;
        }
        return SHIFT_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DATE_TYPE:
                return new DateViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_date_info, parent, false));
            case MEMBER_TYPE:
                return new MemberViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_member_info, parent, false));
            case SHIFT_TYPE:
                return new ShiftViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_shifts_info, parent, false));
            case TITLE_TYPE:
                return new TitleViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_title, parent, false));
            default:
                break;
        }
        return new ShiftViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_shifts_info, parent, false));
    }


    private void setDateView(int pos, DateViewHolder viewHolder) {
        DateInfo dateInfo = dateInfoList.get(pos - 1);
        if (dateInfo != null && pos > 0) {
            viewHolder.dateTextView.setText(dateInfo.getDate());
            viewHolder.weekTextView.setText(dateInfo.getWeek());
        }
    }

    private void setMemberView(int pos, MemberViewHolder viewHolder) {
        MemberInfo memberInfo = memberInfoList.get(pos - 1);
        if (memberInfo != null && pos > 0) {
            viewHolder.memberTypeTextView.setText(memberInfo.getMemberType());
            viewHolder.memberNameTextView.setText(memberInfo.getMemberName());
        }
    }

    private void setShiftView(final int row, final int column, ShiftViewHolder viewHolder) {
        final ShiftInfo shiftInfo = shiftsList.get(row - 1).get(column - 1);
        if (shiftInfo != null) {
            if (shiftInfo.getStatus() == ShiftInfo.Status.OFF) {
                viewHolder.view.setBackgroundResource(R.drawable.bg_white_gray_stroke);
                viewHolder.nameTextView.setText("");
                viewHolder.statusTextView.setText("");
            } else if (shiftInfo.getStatus() == ShiftInfo.Status.DAY) {
                viewHolder.nameTextView.setText(shiftInfo.isBegin() ? shiftInfo.getWorkerName() : ""); //isBegin 이 true 이면 첫번째 state 아니면 "" 공란.
                viewHolder.statusTextView.setText(shiftInfo.isBegin() ? "DAY" : ""); //이것도 그러하다..!
                viewHolder.view.setBackgroundResource(shiftInfo.isBegin() ? R.drawable.bg_member_red_begin_with_stroke : R.drawable.bg_member_red_with_stroke);
            } else if (shiftInfo.getStatus() == ShiftInfo.Status.EVENING) {
                viewHolder.nameTextView.setText(shiftInfo.isBegin() ? shiftInfo.getWorkerName() : "");
                viewHolder.statusTextView.setText(shiftInfo.isBegin() ? "EVENING" : "");
                viewHolder.view.setBackgroundResource(shiftInfo.isBegin() ? R.drawable.bg_member_blue_begin_with_stroke : R.mipmap.bg_member_blue_middle);
            } else if (shiftInfo.getStatus() == ShiftInfo.Status.NIGHT) {
                viewHolder.nameTextView.setText(shiftInfo.isBegin() ? shiftInfo.getWorkerName() : "");
                viewHolder.statusTextView.setText(shiftInfo.isBegin() ? "NIGHT" : "");
                viewHolder.view.setBackgroundResource(shiftInfo.isBegin() ? R.drawable.bg_member_black_begin_with_stroke : R.mipmap.bg_room_dark);
            }

            //OnClickListener 구현 부분 start --------------------------------------------------------------------------------------------------------------------------------
            if (shiftInfo.getStatus() != ShiftInfo.Status.OFF) {
                viewHolder.itemView.setClickable(true);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (shiftInfo.isBegin()) {
                            Toast.makeText(v.getContext(), "근무자:" + shiftInfo.getWorkerName()+ "\n" + shiftInfo.getWorkerName(), Toast.LENGTH_SHORT).show();
                        } else {
                            int i = 2;
                            while (column - i >= 0 && shiftsList.get(row - 1).get(column - i).getId() == shiftInfo.getId()) {
                                i++;
                            }
                            final ShiftInfo info = shiftsList.get(row - 1).get(column - i + 1);
                            Toast.makeText(v.getContext(), "근무자:" + info.getWorkerName() + "\n" + info.getWorkerName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                viewHolder.itemView.setClickable(false);
            }
            //OnClickListener 구현 부분 ends --------------------------------------------------------------------------------------------------------------------------------
        }
    }


    private static class DateViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView weekTextView;

        public DateViewHolder(View itemView) {
            super(itemView);
            this.dateTextView = (TextView) itemView.findViewById(R.id.date);
            this.weekTextView = (TextView) itemView.findViewById(R.id.week);
        }

    }

    private static class MemberViewHolder extends RecyclerView.ViewHolder {
        public TextView memberTypeTextView;
        public TextView memberNameTextView;

        public MemberViewHolder(View view) {
            super(view);
            this.memberTypeTextView = (TextView) view.findViewById(R.id.room_type);
            this.memberNameTextView = (TextView) view.findViewById(R.id.room_name);
        }
    }

    private static class ShiftViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView statusTextView;
        public View view;

        public ShiftViewHolder(View view) {
            super(view);
            this.view = view;
            this.statusTextView = (TextView) view.findViewById(R.id.status);
            this.nameTextView = (TextView) view.findViewById(R.id.worker_name);
        }
    }

    private static class TitleViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;

        public TitleViewHolder(View view) {
            super(view);
            this.titleTextView = (TextView) view.findViewById(R.id.title);
        }
    }


    public void setMemberInfoList(List<MemberInfo> memberInfoList) {
        this.memberInfoList = memberInfoList;
    }

    public void setDateInfoList(List<DateInfo> dateInfoList) {
        this.dateInfoList = dateInfoList;
    }

    public void setShiftsList(List<List<ShiftInfo>> shiftsList) {
        this.shiftsList = shiftsList;
    }


}
