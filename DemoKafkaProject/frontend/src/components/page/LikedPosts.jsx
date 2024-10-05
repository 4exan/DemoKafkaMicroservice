import { useEffect, useState } from "react";
import PostService from "../service/PostService";
import Post from "../common/Post";
import { Link } from "react-router-dom";

export default function LikedPosts() {
  const [loading, setLoading] = useState(true);
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    setLoading(true);
    try {
      const token = localStorage.getItem("token");
      const likeData = await PostService.getUserLikes(token);
      setPosts(likeData.postList);
    } catch (error) {
      throw error;
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex h-screen">
      {/* LEFT PANEL */}
      <div className="w-1/3 flex flex-col items-start justify-start p-4">
        <div>
          <h1 className="text-3xl font-semibold">Liked posts!</h1>
        </div>
      </div>
      {/* CENTRAL PANEL */}
      <div className="w-2/5 flex items-start justify-center border-x-2 border-gray-300 shadow-black shadow-2xl bg-gray-300">
        <div className="flex-grow overflow-y-scroll max-h-[calc(100vh-100px)] max-w-[calc(100vh-350px)] table-scroll">
          <table>
            <thead></thead>
            <tbody>
              {posts.map((post) => (
                <tr>
                  <td>
                    <div className="border-2 border-gray-400 rounded-lg my-4">
                      <Link
                        to={`/user/search/${post.username}`}
                        state={{ username: `${post.username}` }}
                        className="px-2"
                      >
                        {post.username}
                      </Link>
                      <Post post={post} />
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      {/* RIGHT PANEL */}
      <div className="w-1/3 flex items-center justify-center"></div>
    </div>
  );
}
