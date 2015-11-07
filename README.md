## UPDATE INFORMATION
7/11/2015 <yunita3>
####Updates:
- friends requirements
- inventory works! (adding, removing, listing item)
####TODO for Project 4 to Project 5:
- view item after added to inventory
- edit item in inventory
####Information:
1. "adding friend" and "removing friend" now is one way.
(A adds B, A friends with B, but B does not friend with A, see Dr. Hindle clarification)
2. removed the same or unused methods (see git commit for reference)
3. User now has inventory and friends. Since inventory and friends are the part of user, so when we want to make a change on inventory or friends, updateUser (in userController) must be called after you made the change. 
4. Since "our friends requirement" is changed, instead of using "elastic search" to keep updating friend list, we just need to store the new value to LoginActivity.USERLOGIN and then run "updateUserThread". (refer to point 1, 3, and friendController and userController)
5. most methods name now is matching with class name; and class name is matching with directory name(ie, updateUser -> userController, userController ->  dir/user)
6. LoginActivity.USERLOGIN is static, so it means as long the app is still running, it shares the values across the classes.
7. Pretty much the method name describes everything.
8. SearchController -> UserController
9. Inventory is the list of ITEM. What you have to add is the item, not the inventory. 
10. Tracing the code and the bug are not easy, System.out.println() and Toast can be helpful! 
11. for now our webserver link (it contains user): http://cmput301.softwareprocess.es:8080/cmput301f15t09/inv/[username]

## TradioGC
A trading gift-card application

## Specifications
- Compile Sdk Version: 21
- Build ToolsVersion: 22.0.1
- Min Sdk Version: 18
- Target Sdk Version: 21

## Demo
Here is the [demo](https://github.com/CMPUT301F15T09/Cloud9/blob/yunita/docs/demo/tradiogc_demo_project4.mp4) for project 4

## Documentation
You can find our documentation in[our wiki](https://github.com/CMPUT301F15T09/Cloud9/wiki)

## Contributions
- user1338795, http://stackoverflow.com/questions/5730609/is-it-possible-to-slowdown-reaction-of-edittext-listener
- Designmodo, https://www.iconfinder.com/designmodo
- Joshua Campbell, https://github.com/joshua2ua/AndroidElasticSearch

## License
TradioGC is licensed under Apache License Version 2.0.
