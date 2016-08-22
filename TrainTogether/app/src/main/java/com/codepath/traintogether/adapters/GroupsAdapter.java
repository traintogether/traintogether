package com.codepath.traintogether.adapters;

/**
 * Created by ameyapandilwar on 8/21/16 at 10:02 PM.
 */
//public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {
//
//    private static final String TAG = "GroupAdapter";
//    private Context mContext;
//    private DatabaseReference mDatabaseReference;
//    private ChildEventListener mChildEventListener;
//
//    private List<Group> mGroups = new ArrayList<>();
//
//    public GroupAdapter(final Context context, DatabaseReference ref) {
//        mContext = context;
//        mDatabaseReference = ref;
//
//        // Create child event listener
//        // [START child_event_listener_recycler]
//        ChildEventListener childEventListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
//
//                // A new comment has been added, add it to the displayed list
//                Group group = dataSnapshot.getValue(Group.class);
//
//                // [START_EXCLUDE]
//                // Update RecyclerView
//                mGroups.add(group);
//                notifyItemInserted(mGroups.size() - 1);
//                // [END_EXCLUDE]
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so displayed the changed comment.
//                Group newGroup = dataSnapshot.getValue(Group.class);
//                String commentKey = dataSnapshot.getKey();
//
//                // [START_EXCLUDE]
//                int commentIndex = mCommentIds.indexOf(commentKey);
//                if (commentIndex > -1) {
//                    // Replace with the new data
//                    mGroups.set(commentIndex, newGroup);
//
//                    // Update the RecyclerView
//                    notifyItemChanged(commentIndex);
//                } else {
//                    Log.w(TAG, "onChildChanged:unknown_child:" + commentKey);
//                }
//                // [END_EXCLUDE]
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so remove it.
//                String commentKey = dataSnapshot.getKey();
//
//                // [START_EXCLUDE]
//                int commentIndex = mCommentIds.indexOf(commentKey);
//                if (commentIndex > -1) {
//                    // Remove data from the list
//                    mCommentIds.remove(commentIndex);
//                    mComments.remove(commentIndex);
//
//                    // Update the RecyclerView
//                    notifyItemRemoved(commentIndex);
//                } else {
//                    Log.w(TAG, "onChildRemoved:unknown_child:" + commentKey);
//                }
//                // [END_EXCLUDE]
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
//
//                // A comment has changed position, use the key to determine if we are
//                // displaying this comment and if so move it.
//                Comment movedComment = dataSnapshot.getValue(Comment.class);
//                String commentKey = dataSnapshot.getKey();
//
//                // ...
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
//                Toast.makeText(mContext, "Failed to load comments.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        };
//        ref.addChildEventListener(childEventListener);
//        // [END child_event_listener_recycler]
//
//        // Store reference to listener so it can be removed on app stop
//        mChildEventListener = childEventListener;
//    }
//
//    @Override
//    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
//        return new GroupViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(GroupViewHolder holder, int position) {
//        Group group = mGroups.get(position);
//        holder.authorView.setText(group.eventId);
//        holder.bodyView.setText(group.getUsers().toString());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mGroups.size();
//    }
//
//    public void cleanupListener() {
//        if (mChildEventListener != null) {
//            mDatabaseReference.removeEventListener(mChildEventListener);
//        }
//    }
//
//    private static class GroupViewHolder extends RecyclerView.ViewHolder {
//
//        public TextView titleView;
////        public TextView bodyView;
//
//        public GroupViewHolder(View itemView) {
//            super(itemView);
//
//            titleView = (TextView) itemView.findViewById(R.id.title_text);
////            bodyView = (TextView) itemView.findViewById(R.id.comment_body);
//        }
//    }
//
//}