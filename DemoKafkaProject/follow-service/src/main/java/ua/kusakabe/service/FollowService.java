package ua.kusakabe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.kusakabe.dto.FollowRR;
import ua.kusakabe.dto.UserWithPost;
import ua.kusakabe.entity.Follow;
import ua.kusakabe.entity.Post;
import ua.kusakabe.repository.FollowRepository;
import ua.kusakabe.repository.PostRepository;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final JwtService jwtService;
    private final RestTemplate restTemplate;
    private final PostRepository postRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(FollowService.class);

    @Autowired
    public FollowService(FollowRepository followRepository, JwtService jwtService, RestTemplate restTemplate, PostRepository postRepository) {
        this.followRepository = followRepository;
        this.jwtService = jwtService;
        this.restTemplate = restTemplate;
        this.postRepository = postRepository;
    }

    public void unfollowUser(String username, String header) {
        try{
            String follower = jwtService.extractUsername(header.substring(7));
            Follow existedFollow = followRepository.findByFollowerAndFollowed(follower, username).orElseThrow(()-> new RuntimeException("No such following present!"));
            if(existedFollow != null){
                followRepository.delete(existedFollow);
                LOGGER.info("Unfollowed user : {}", username);
            } else {
                LOGGER.warn("Username is blank!");
            }
        }catch (Exception e){
            LOGGER.error("Error while unfollowing user -> ", e);
        }
    }

    public boolean isUserFollowed(String header, String username) {
        try{
            String follower = jwtService.extractUsername(header.substring(7));
            return followRepository.findByFollowerAndFollowed(follower, username).isPresent();
        } catch (Exception e){
            LOGGER.error("Error while checking user follow has occurred -> ", e);
        }
        return false;
    }

    public FollowRR getUserFollows(String username) {
        FollowRR res = new FollowRR();
        try{
            List<Follow> dbList = followRepository.findAllByFollower(username);
            if(!dbList.isEmpty()){
                res.setStatusCode(200);
                res.setMessage("User follows loaded successfully!");
                List<String> followedList = new ArrayList<>();
                for(Follow follower : dbList){
                    followedList.add(follower.getFollowed());
                }
//                res.setFollows(dbList);
                res.setFollowedList(followedList);
            } else {
                res.setStatusCode(404);
                res.setMessage("User followers not found!");
            }
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public FollowRR getUserFollowers(String username) {
        FollowRR res = new FollowRR();
        try{
            List<Follow> dbList = followRepository.findAllByFollowed(username);
            if(!dbList.isEmpty()){
                res.setStatusCode(200);
                res.setMessage("User followers loaded successfully!");
                List<String> followerList = new ArrayList<>();
                for(Follow follower : dbList){
                    followerList.add(follower.getFollower());
                }
                res.setFollowedList(followerList);
            } else {
                res.setStatusCode(404);
                res.setMessage("User follow list is empty!");
            }
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

//    public FollowRR testRequestToPostService(String header){
//        FollowRR res = new FollowRR();
//        List<UserWithPost> resList = new ArrayList<>();
//        String username = jwtService.extractUsername(header.substring(7));
//        try{
//            List<Follow> dbList = followRepository.findAllByFollower(username);
//            if(!dbList.isEmpty()){
//                res.setStatusCode(200);
//                res.setMessage("User follows loaded successfully!");
//                List<String> followedList = new ArrayList<>();
//                for(Follow follower : dbList){
//                    List<Post> postList = postRepository.findAllByUsername(follower.getFollowed());
//                    UserWithPost userWithPost = new UserWithPost();
//                    userWithPost.setUsername(follower.getFollowed());
//                    userWithPost.setPosts(postList);
//                    resList.add(userWithPost);
//                }
////                res.setFollows(dbList);
//                res.setFollowedPosts(resList);
//            } else {
//                res.setStatusCode(404);
//                res.setMessage("User followers not found!");
//            }
//        } catch (Exception e) {
//            res.setStatusCode(500);
//            res.setMessage(e.getMessage());
//        }
//        return res;
//    }

    public FollowRR testRequestToPostService(String header){
        FollowRR res = new FollowRR();
        String username = jwtService.extractUsername(header.substring(7));
        try {
            List<Follow> dbList = followRepository.findAllByFollower(username);
            if (!dbList.isEmpty()) {
                List<Post> postList = new ArrayList<>();
                for(Follow followed : dbList){
                    postList.addAll(postRepository.findAllByUsername(followed.getFollowed()));
                }
                res.setPostList(postList);
                LOGGER.info("Loaded feed posts for {}", username);
//                List<UserWithPost> resList = dbList.stream()
//                        .map(follow -> {
//                            UserWithPost userWithPost = new UserWithPost();
//                            userWithPost.setUsername(follow.getFollower());
//                            userWithPost.setPosts(follow.getFollowed());
//                            return userWithPost;
//                        })
//                        .collect(Collectors.toList());
//
//                res.setStatusCode(200);
//                res.setMessage("User follows loaded successfully!");
//                res.setFollowedPosts(resList);
            } else {
                res.setStatusCode(404);
                res.setMessage("User followers not found!");
            }
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
