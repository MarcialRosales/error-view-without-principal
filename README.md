# Error View has no access to Authentication object 

We would like to include the current user's name in the error template view. If the user is not logged in, we may want to include a link to login or nothing. But it turns out the error view has no access to the Spring Authentication object of the current user.

Steps to reproduce the issue:
<ul><li>Start up the spring boot app by running ErrorViewWithoutPrincipalApplication</li>
<li>Execute test case ErrorViewWithoutPrincipalApplicationTests: 2 test cases will pass and 1 will fail because it cannot find the logged in user who is authenticated but has no the required role.</li>
</ul>


