import { useEffect, useState } from "react";
import UserService from "../../service/UserService";
import FollowService from "../../service/FollowService";

export default function ProfileSection({ token, profile, isOwner, openEdit }) {
  const [isFollowed, setIsFollowed] = useState(profile.followed);
  const [profilePicture, setProfilePicture] = useState(null);

  useEffect(() => {
    //fetchData();
    configureData();
  }, []);

  const configureData = () => {
    if (profile?.profilePictureBytes) {
      const binary = atob(profile.profilePictureBytes);
      const array = [];
      for (let i = 0; i < binary.length; i++) {
        array.push(binary.charCodeAt(i));
      }
      const blob = new Blob([new Uint8Array(array)], { type: "image/png" });
      const url = URL.createObjectURL(blob);
      setProfilePicture(() => url);
    } else {
      console.log("Can't show picture!");
      setProfilePicture(() => null);
    }
  };

  const fetchData = async () => {
    if (!isOwner) {
      try {
        const [isFollowed, profilePicture] = await Promise.all([
          fetchIsFollowed(),
          downloadImage(),
        ]);
        configureData(isFollowed, profilePicture);
      } catch (e) {
        throw e;
      }
    } else {
      setProfilePicture(() => null);
      const pfp = await downloadImage();
      if (pfp.data.size != 0) {
        const imageUrl = URL.createObjectURL(pfp.data);
        setProfilePicture(() => imageUrl);
      } else {
        setProfilePicture(() => null);
      }
    }
  };

  /* const configureDataOld = (isFollowed, pfp) => { 
      console.log("Image: ", pfp.data.size);
    setIsFollowed(isFollowed.data);
    if(pfp.data.size != 0){
    const imageUrl = URL.createObjectURL(pfp.data);
    setProfilePicture(()=>imageUrl);
    } else {
      setProfilePicture(()=>null)
    }
  };
*/

  const handleFollow = async () => {
    // LOGIC FOR WRITING FOLLOW IN DB
    FollowService.followUser(token, profile.username);
    setIsFollowed(() => !isFollowed);
  };

  const handleUnfollow = async () => {
    // LOGIC FOR DELETING FOLLOW FROM DB
    FollowService.unfollowUser(token, profile.username);
    setIsFollowed(() => !isFollowed);
  };

  const fetchIsFollowed = () => {
    try {
      const response = FollowService.isUserFollowed(token, profile.username);
      return response;
    } catch (e) {
      throw e;
    }
  };

  const downloadImage = () => {
    try {
      const response = UserService.downloadImage(profile.username, token);
      return response;
    } catch (error) {
      console.error("Error fetching image:", error);
      setProfilePicture(null); // Очищаємо попереднє зображення, якщо є помилка
    }
  };

  return (
    <div className="w-1/3 flex flex-col items-start justify-start  p-4 bg-lyra-white">
      <div className="w-96 h-96 rounded-full overflow-hidden">
        <img
          src={profilePicture ? profilePicture : "https://placehold.co/150x150"}
          //className="w-full h-auto max-w-xs mb-4 rounded-full"
          className="w-full h-full object-cover"
          alt="Profile picture"
        />
      </div>
      <div className="border-b-2 border-gray-400 w-1/2">
        <p className="font-semibold text-3xl py-2">
          {profile.firstName} {profile.lastName}
        </p>
      </div>
      <div className="my-2">
        {isOwner ? (
          <button
            className="px-2 border-2 border-black rounded transition-all hover:border-transparent hover:bg-slate-700 hover:text-white active:font-semibold"
            onClick={openEdit}
          >
            Edit
          </button>
        ) : (
          <button
            className={
              isFollowed
                ? `px-2 transition-all bg-green-600 text-white border-2 border-transparent rounded-lg hover:bg-transparent hover:border-black hover:text-black`
                : `px-2 transition-all bg-transparent text-black border-2 border-black rounded-lg hover:bg-green-600 hover:border-transparent hover:text-white`
            }
            onClick={isFollowed ? handleUnfollow : handleFollow}
          >
            {isFollowed ? "Unfollow" : "Follow"}
          </button>
        )}
      </div>
      <div className="">
        <p className="font-semibold text-xl font-montserrat pt-2">
          Username:{" "}
          <span className="font-normal text-xl mx-2 font-montserrat">
            {profile.username}
          </span>
        </p>
        <p className="font-semibold text-xl font-montserrat">
          Email:
          <span className="font-normal text-xl mx-2 font-montserrat">
            {profile.email}
          </span>
        </p>
        <p className="font-semibold text-xl font-montserrat">
          Phone:
          <span className="font-normal text-xl mx-2 font-montserrat">
            {profile.phone}
          </span>
        </p>
      </div>
    </div>
  );
}
