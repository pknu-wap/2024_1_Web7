import { useState } from "react";
import "./StarRating.css";

function StarRating({
  max = 5,
  size = 18,
  color = "#FF7272",
  onSetRate,
  defaultRate = 1,
  className,
}) {
  const [rate, setRate] = useState(defaultRate);
  const [mouseOverRate, setMouseOverRate] = useState(0);

  const textStyle = {
    fontSize: `${size / 1.5}px`,
    margin: "0",
    lineHeight: "1",
    color,
  };

  const onClick = (r) => {
    setRate(r);
    onSetRate(r);
  };

  return (
    <div className="starRate-box">
      <div className="starRate">
        {Array.from({ length: max }, (_, i) => (
          <Star
            key={i}
            size={size}
            color={color}
            filled={mouseOverRate ? mouseOverRate >= i + 1 : rate >= i + 1}
            onClick={() => onClick(i + 1)}
            onMouseEnter={() => setMouseOverRate(i + 1)}
            onMouseLeave={() => setMouseOverRate(0)}
          />
        ))}
      </div>
      {/* <p>{mouseOverRate ? mouseOverRate : rate}</p> */}
    </div>
  );
}

function Star({ size, color, filled, onClick, onMouseEnter, onMouseLeave }) {
  const starStyle = {
    display: "block",
    width: `${size}px`,
    height: `${size}px`,
    cursor: "pointer",
  };

  return (
    <span
      style={starStyle}
      role="button"
      onClick={onClick}
      onMouseEnter={onMouseEnter}
      onMouseLeave={onMouseLeave}
    >
      {filled ? (
        <svg
          id="Icons"
          viewBox="0 0 24 24"
          xmins="http://www.w3.org/2000/svg"
          fill={color}
        >
          <defs>
            <style>.cls-1</style>
            <linearGradient
              gradientUnits="userSpaceOnUse"
              id="linear-gradient"
              x1="12"
              x2="12"
              y1="1.755"
              y2="23.076"
            >
              <stop offset="0" stopColor="#fff650" />
              <stop offset="1" stopColor="#ffab17" />
            </linearGradient>
          </defs>
          <path
            className="cls-1"
            d="M12.992,20.912l3.5,1.838A2.131,2.131,0,0,0,19.58,20.5l-.667-3.893a2.129,2.129,0,0,1,.613-1.887l2.828-2.757a2.131,2.131,0,0,0-1.181-3.635l-3.909-.568a2.133,2.133,0,0,1-1.6-1.166L13.911,3.056a2.131,2.131,0,0,0-3.822,0L8.341,6.6a2.133,2.133,0,0,1-1.6,1.166l-3.909.568a2.131,2.131,0,0,0-1.181,3.635l2.828,2.757a2.129,2.129,0,0,1,.613,1.887L4.42,20.5A2.131,2.131,0,0,0,7.512,22.75l3.5-1.838A2.135,2.135,0,0,1,12.992,20.912Z"
          />
        </svg>
      ) : (
        <svg
          viewBox="0 0 576 512"
          xmlns="http://www.w3.org/2000/svg"
          fill={color}
        >
          <path d="M528.1 171.5L382 150.2 316.7 17.8c-11.7-23.6-45.6-23.9-57.4 0L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6zM388.6 312.3l23.7 138.4L288 385.4l-124.3 65.3 23.7-138.4-100.6-98 139-20.2 62.2-126 62.2 126 139 20.2-100.6 98z" />
        </svg>
      )}
    </span>
  );
}

export default StarRating;
