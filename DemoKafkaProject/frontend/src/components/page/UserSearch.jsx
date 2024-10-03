import { useState } from "react";
import { useLocation, useNavigate } from "react-router";
import UserService from "../service/UserService";
import Post from "../common/Post";
import PostService from "../service/PostService";

export default function UserSearch() {
  const [username, setUsername] = useState("");
  const [userPage, setUserPage] = useState({});
  const [userPosts, setUserPosts] = useState([]);
  const [inSearch, setInSearch] = useState(false);
  // const {username} = useLocation();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("token");
      const response = await UserService.getUserProfile(token, username);
      fetchUserPosts();
      setUsername(() => "");
      setUserPage(() => response);
      if (userPage.username != "") {
        setInSearch(() => true);
      }
    } catch (error) {
      throw error;
    }
  };

  const fetchUserPosts = async (e) => {
    try {
      const token = localStorage.getItem("token");
      const response = await PostService.getUserPosts(token, username);
      setUserPosts(() => response.postList);
      console.log(userPosts); //REMOVE
    } catch (error) {
      throw error;
    }
  };

  return (
    <div className="flex h-screen">
      {/* Ліва частина (30%) */}
      <div className="w-1/3 bg-blue-200 flex items-center justify-center">
        <h2>Left Column</h2>
      </div>

      {/* Центральна частина (40%) */}
      <div className="w-2/5 bg-green-200 flex items-center justify-center">
        <h2>Center Column</h2>
      </div>

      {/* Права частина (30%) */}
      <div className="w-1/3 bg-red-200 flex items-center justify-center">
        <h2>Right Column</h2>
      </div>
    </div>
  );

  // return (
  //   <>
  //     <div>
  //       <h1 className="text-center font-montserrat font-semibold text-3xl p-2">
  //         User search!
  //       </h1>
  //       <div className="relative left-1/2">
  //         <form className="mx-4" onSubmit={handleSubmit}>
  //           <input
  //             type="text"
  //             className="border-2 mx-2 transition-all hover:border-black rounded-lg p-2"
  //             value={username}
  //             onChange={(e) => setUsername(e.target.value)}
  //           />
  //           <input
  //             type="submit"
  //             value="Search"
  //             className="cursor-pointer transition-all hover:bg-black hover:text-white p-2 rounded-lg"
  //           />
  //         </form>
  //       </div>
  //     </div>
  //     {inSearch && (
  //       <div className="px-10 py-5" id="profile">
  //         <h1 className="font-semibold text-2xl text-center font-montserrat">
  //           Profile page!
  //         </h1>
  //         <div>
  //           <p className="font-semibold text-xl font-montserrat">
  //             Username:{" "}
  //             <span className="font-normal text-xl mx-2 font-montserrat">
  //               {userPage.username}
  //             </span>
  //           </p>
  //           <p className="font-semibold text-xl font-montserrat">
  //             Full name:
  //             <span className="font-normal text-xl mx-2 font-montserrat">
  //               {userPage.firstName} {userPage.lastName}
  //             </span>
  //           </p>
  //           <p className="font-semibold text-xl font-montserrat">
  //             Email:
  //             <span className="font-normal text-xl mx-2 font-montserrat">
  //               {userPage.email}
  //             </span>
  //           </p>
  //           <p className="font-semibold text-xl font-montserrat">
  //             Phone:
  //             <span className="font-normal text-xl mx-2 font-montserrat">
  //               {userPage.phone}
  //             </span>
  //           </p>
  //         </div>
  //         <div>
  //           <h1 className="text-center font-bold text-2xl my-4">Posts!</h1>
  //           <div className="table-container">
  //             <table className="">
  //               <thead className=""></thead>
  //               <tbody>
  //                 {userPosts.map((post) => (
  //                   <tr className="">
  //                     <td className="break-normal">
  //                       <Post post={post} key={post.id} />
  //                     </td>
  //                     <td className="px-4"></td>
  //                   </tr>
  //                 ))}
  //               </tbody>
  //             </table>
  //           </div>
  //         </div>
  //       </div>
  //     )}
  //   </>
  // );
}
