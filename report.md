Red and blue agents
1.      Describe your design of the message potency (a.k.a. uncertainty of red and blue nodes. )

Our design of the message potency is to use the level of the level of the message to map to an uncertainty
with higher level of message having a lower uncertainty. We use the following formula to calculate the
uncertainty of the message:

uncertainty = 1 - (level * 2 / max_level)

where max_level is the maximum possible level of the message. The level of the message is linearly
mapped to the uncertainty of the message.

2.      Describe your method for changing the followers' number in case of red agent

When the red agent sends a message, it's broadcasted to all the followers of the red agent. The
followers of the red agent will have a chance to unfollow the red agent before they are effected by the
message. The chance of unfollowing the red agent is complexly related to:
1.      the uncertainty of the message. - the lower the uncertainty, the higher the chance of a green unfollowing the red agent.
2.      the opinion of the follower. - only the followers with the opposite opinion as the red agent will have a chance to unfollow the red agent.
3.      The uncertainty of the follower. - the lower the uncertainty of the follower, the higher the chance of unfollowing the red agent.

the chance of unfollowing the red agent is calculated by the following formula:

chance = 1 - (uncertainty of the message * uncertainty of the follower * opinion of the follower)

3.      Describe your method for changing the energy level of blue nodes (a.k.a. lifeline)
4.      Other Pertinent points regarding the working of your agent