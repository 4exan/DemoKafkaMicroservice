import { useEffect, useState } from "react";
import PostService from "../service/PostService";

export default function Post({
  post,
  removePost,
  likePost,
  dislikePost,
  user,
  postOwner,
}) {
  const [isLiked, setIsLiked] = useState(post.isLiked);

  function handleShowLike() {
    if (isLiked) {
      return "Dislike";
    } else {
      return "Like";
    }
  }

  function handleLikePost() {
    likePost(post.id);
    setIsLiked(true);
  }

  function handleDislikePost() {
    dislikePost(post.id);
    setIsLiked(false);
  }

  return (
    <div className="max-w-[calc(100vh-370px)] min-w-96">
      <div className="text-left border-x-2 border-y-2 border-gray-600 h-auto rounded-t-md p-2 mt-0 bg-white">
        <h1 className="text-xl font-semibold" title={post.timestamp}>
          {post.title}
        </h1>
        <p className="break-words">{post.content}</p>
      </div>
      <div className="border-x-2 border-gray-600 border-b-2 rounded-b-lg w-fit bg-gray-200">
        <button
          className={
            isLiked
              ? `px-2 bg-red-600 font-semibold text-white rounded-bl-md hover:bg-black hover:text-white transition-all`
              : `px-2 rounded-bl-md hover:bg-red-600 hover:text-white transition-all`
          }
          onClick={isLiked ? () => handleDislikePost() : () => handleLikePost()}
        >
          {handleShowLike()}
        </button>
        {postOwner ? (
          <button
            className="px-2 hover:bg-red-600 hover:text-white transition-all"
            onClick={() => removePost(post.id)}
          >
            Delete
          </button>
        ) : (
          <></>
        )}
        <button className="px-2 hover:bg-slate-700 hover:rounded-br-md hover:text-white transition-all">
          Comments
        </button>
      </div>
    </div>
  );
}
