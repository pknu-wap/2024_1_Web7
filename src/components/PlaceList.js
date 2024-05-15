import "./PlaceList.css";

function PlaceListItem({ item }) {
  const { name, images, isOpen, placeType, rate, address } = item;

  return (
    <div className="PlaceListItem">
      <div className="PlaceListItem-info">
        <div className="PlaceListItem-name-type-rate">
          <h1 className="PlaceListItem-place-name">{name}</h1>
          <span className="PlaceListItem-place-type">{placeType}</span>
          <span className="PlaceListItem-place-rate">{rate}</span>
        </div>
        <div className="PlaceListItem-inopen-add">
          {{ isOpen } ? "영업 중" : "금일 영업 마감"}
        </div>
        <div className="PlaceListItem-image-container">
          {images.map((image, index) => {
            const base64URL = image.data;
            const imageURL = `data:image/jpeg;base64,${base64URL}`;

            return (
              <img
                key={index}
                className="PlaceListItem-place-img"
                src={imageURL}
                alt={`${name}_${index}`}
              />
            );
          })}
        </div>
      </div>
    </div>
  );
}

function PlaceList({ className = "", items }) {
  return (
    <ul className={`PlaceList ${className}`}>
      {items.map((item) => {
        return (
          <li className="PlaceList-list" key={item.id}>
            <PlaceListItem item={item} />
          </li>
        );
      })}
    </ul>
  );
}

export default PlaceList;
