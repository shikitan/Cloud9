package com.example.yunita.tradiogc.friends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yunita.tradiogc.R;
import com.example.yunita.tradiogc.login.LoginActivity;
import com.example.yunita.tradiogc.profile.ProfileActivity;


public class FriendsActivity extends AppCompatActivity {

    private Context context = this;
    private Friends friends;
    private ListView friendList;
    private String friendname;
    private FriendsController friendsController;
    private ArrayAdapter<String> friendsViewAdapter;
    private Runnable doUpdateGUIDetails = new Runnable() {
        public void run() {
            friendsViewAdapter.remove(friendname);
            friendsViewAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        friendsController = new FriendsController(context);
        friendList = (ListView) findViewById(R.id.friend_list_view);
        friends = LoginActivity.USERLOGIN.getFriends();
        System.out.println("friends: " + friends.size());
    }

    /**
     * Sets up the friends view adapter and manipulate the list view.
     * While the item list is clicked, it sends user to friend pofile.
     * While the item list is long clicked, it removes friend from
     * the user friend list and calls delete thread.
     */
    @Override
    protected void onStart() {
        super.onStart();

        friendsViewAdapter = new ArrayAdapter<String>(this, R.layout.friend_list_item, friends);
        friendList.setAdapter(friendsViewAdapter);

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String username = friends.get(position);
                viewFriendProfile(username);
            }
        });
        // Delete friends on long click
        friendList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                friendname = friends.get(position);
                Thread deleteThread = new DeleteFriendThread(friendname);
                deleteThread.start();
                Toast.makeText(context, "Deleting " + friendname, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    /**
     * Updates user's friends everytime the user going back to friends page.
     */
    @Override
    public void onResume() {
        super.onResume();
        // keep updating friend list (since we use tabmenu, after first
        // visit, this activity will be on resume/pause).
        friends = LoginActivity.USERLOGIN.getFriends();
    }

    /**
     * Called when the user clicks "Add New Friend" button.
     * <p>This method is used to send user to search friend page.
     *
     * @param view "Add New Friend" button.
     */
    public void searchUser(View view) {
        Intent intent = new Intent(this, SearchUserActivity.class);
        startActivity(intent);
    }

    /**
     * Called when the user clicks friend's name in list view.
     * <p>This method is used to send user to his friend's profile page.
     *
     * @param username friends's name.
     */
    public void viewFriendProfile(String username) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(ProfileActivity.USERNAME, username);
        startActivity(intent);
    }

    /**
     * Called when the user attempts to delete a friend by long pressing
     * on friend's name.
     * <p>This class creates a thread and runs "Delete Friend".
     * While it is running, it removes this friend in user's friend list
     * and updates the friends list view.
     */
    class DeleteFriendThread extends Thread {
        private String friendname;

        public DeleteFriendThread(String friendname) {
            this.friendname = friendname;
        }

        @Override
        public void run() {
            friendsController.deleteFriend(friendname);
            runOnUiThread(doUpdateGUIDetails);
        }
    }
}
