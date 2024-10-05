import { useState } from "react";
import PostService from "../service/PostService";

export default function PostModal({ setIsOpen }) {
  const [formData, setFormData] = useState({
    title: "",
    content: "",
  });
  const [contentCounter, setContentCounter] = useState(254);
  const [titleCounter, setTitleCounter] = useState(49);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
    if (contentCounter === 0) {
      setContentCounter(() => 1);
    } else {
      setContentCounter(() => 254 - formData.content.length);
    }
    if (formData.title.length == 49) {
      console.warn("Max char");
    } else {
      setTitleCounter(() => 49 - formData.title.length);
    }
    // if (titleCounter > -1) setTitleCounter(() => 49 - formData.title.length);
    // console.log(formData);
  };

  const handleSubmit = async (e) => {
    try {
      e.preventDefault();
      const token = localStorage.getItem("token");
      await PostService.createNewPosts(token, formData);
      setFormData({
        title: "",
        content: "",
      });
      setIsOpen(false);
    } catch (error) {
      throw error;
    }
  };

  return (
    <div className="">
      <div className="modal-bg"></div>
      <div className="modal">
        <h1>New post</h1>
        <button
          onClick={() => setIsOpen(false)}
          className="font-semibold transition-all hover:bg-red-500 hover:text-white hover:border-transparent"
        >
          X
        </button>
        <form className="p-2" onSubmit={handleSubmit}>
          <div className="p-2">
            <label className="text-xl font-semibold">
              Title:{" "}
              <span className=" text-sm font-normal mx-2 opacity-50">
                {titleCounter}
              </span>
            </label>
            <br />
            <input
              type="text"
              className="w-full font-semibold border-2 rounded-md px-2 border-black"
              name="title"
              value={formData.title}
              onChange={handleInputChange}
              maxLength={50}
              required
            ></input>
          </div>
          <div className="p-2">
            <label className="text-xl font-semibold">
              Text:
              <span className=" text-sm font-normal mx-2 opacity-50">
                {contentCounter}
              </span>
            </label>
            <br />
            <textarea
              type="text"
              rows={5}
              className="w-full border-2 rounded-md px-2 border-black"
              name="content"
              value={formData.content}
              onChange={handleInputChange}
              maxLength={254}
              required
            ></textarea>
          </div>
          <div className="text-center">
            <input
              type="submit"
              value="Create post!"
              className="px-4 py-2 rounded-lg border-x-2 transition-all border-black cursor-pointer hover:bg-black hover:text-white"
            />
          </div>
        </form>
      </div>
    </div>
  );
}
