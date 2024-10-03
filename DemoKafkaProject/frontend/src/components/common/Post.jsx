import { useEffect, useState } from "react";

export default function Post({ post }) {
  return (
    <>
      <div className="text-left border-x-2 border-t-2 border-gray-400 h-auto rounded-t-md p-2 mt-2 width800">
        <h1 className="text-xl font-semibold" title={post.timestamp}>
          {post.title}
        </h1>
        <p className="break-words">{post.content}</p>
      </div>
      <div className="border-t-2 border-gray-400"></div>
      <div className="border-x-2 border-gray-400 border-b-2 rounded-b-lg w-1/3">
        {post.isLiked ? (
          <button className="px-2 bg-red-600 font-semibold text-white rounded-bl-md hover:bg-black hover:text-white transition-all w-1/3">
            Dislike
          </button>
        ) : (
          <button className="px-2 rounded-bl-md hover:bg-red-600 hover:text-white hover:font-semibold transition-all w-1/3">
            Like
          </button>
        )}
      </div>
    </>
  );
}
