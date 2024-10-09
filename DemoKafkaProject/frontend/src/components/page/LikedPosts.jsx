import { useEffect, useState } from "react";
import LikeService from "../service/LikeService";
import Post from "../common/Post";
import { Link } from "react-router-dom";

export default function LikedPosts() {
  const [loading, setLoading] = useState(true);
  const [posts, setPosts] = useState([]);
  const [isFiltered, setIsFiltered] = useState(true);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    setLoading(true);
    try {
      const token = localStorage.getItem("token");
      const likeData = await LikeService.getUserLikes(token);
      //   setPosts(likeData.postList);
      extendPosts(likeData.postList);
    } catch (error) {
      throw error;
    } finally {
      setLoading(false);
    }
  };

  function extendPosts(postsR) {
    const extendedPosts = postsR.map((post) => {
      return {
        ...post,
        isLiked: true,
      };
    });
    setPosts(extendedPosts);
  }

  const handleLikeButton = async (id) => {
    try {
      const token = localStorage.getItem("token");
      const response = LikeService.likePost(token, id);
      console.log(token);
      console.log(`Liked post with id: ${id}`);
    } catch (error) {
      throw error;
    }
  };

  const handleDislikeButton = async (id) => {
    try {
      const token = localStorage.getItem("token");
      const response = LikeService.dislikePost(token, id);
      console.log(token);
      console.log(`Disiked post with id: ${id}`);
    } catch (error) {
      throw error;
    }
  };

  const filterPost = () => {
    if (isFiltered) {
      const filteredPosts = posts.sort((a, b) => a.id - b.id);
      setPosts(filteredPosts);
      setIsFiltered(!isFiltered);
    } else {
      const filteredPosts = posts.sort((a, b) => b.id - a.id);
      setPosts(filteredPosts);
      setIsFiltered(!isFiltered);
    }
  };

  if (!loading) {
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
          <div className="flex-grow overflow-y-scroll max-h-[calc(100vh-100px)] max-w-[calc(100vh-350px)] table-scroll scroll-smooth">
            <div className="my-4 m-auto h-1/2 border-b border-x py-2 rounded-lg border-gray-400 shadow-lg bg-white">
              <button
                className="mx-2 text-m font-semibold transition-all hover:bg-black hover:text-white border-2 border-black px-3 rounded-full align-text-bottom"
                onClick={filterPost}
              >
                Filter
              </button>
            </div>

            <table>
              <thead></thead>
              <tbody>
                {posts.map((post) => (
                  <tr>
                    <td>
                      <div className="border-x-2 border-t-2 bg-gray-200 border-gray-600 rounded-t-lg mt-4 w-fit ml-2 hover:bg-slate-700 hover:text-white hover:font-semibold">
                        <Link
                          to={`/user/search/${post.username}`}
                          state={{ username: `${post.username}` }}
                          className="px-2"
                        >
                          {post.username}
                        </Link>
                      </div>
                      <Post
                        post={post}
                        key={post.id}
                        removePost={null}
                        likePost={handleLikeButton}
                        dislikePost={handleDislikeButton}
                        user={null}
                        postOwner={false}
                      />
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
  } else {
    return (
      <div className="loader absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2"></div>
    );
  }
}
