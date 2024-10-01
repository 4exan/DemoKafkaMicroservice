export default function Post({ post }) {
  return (
    <div className="text-left border-2 border-gray-700 h-auto rounded-md p-2 my-2 width800">
      <h1 className="text-xl font-semibold" title={post.timestamp}>
        {post.title}
      </h1>
      <p className="break-words">{post.content}</p>
    </div>
  );
}
