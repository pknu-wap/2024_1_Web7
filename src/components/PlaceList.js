import "./PlaceList.css";

function PlaceListItem({ item }) {
  const { name, image, isOpen, placeType, dogSize, rate } = item;

  return (
    <div className="PlaceListItem">
      <div className="PlaceListItem-info">
        <div className="name-type-rate">
          <h1 className="place-name">{name}</h1>
          <span className="place-type">{placeType}</span>
          <span className="place-rate">{rate}</span>
        </div>
        <div className="inopen-add">
          {{ isOpen } ? "영업 중" : "금일 영업 마감"}
        </div>
        <img className="place-img" src={image.data} alt={name} />
      </div>
    </div>
  );
}

function PlaceList({ className = "", items }) {
  return (
    <ul className={`PlaceList ${className}`}>
      {items.map((item) => {
        const { id, image, name, placeType, rate } = item;

        return (
          <li key={item.id}>
            <PlaceListItem item={item} />
          </li>
        );
      })}
    </ul>
  );
}

export default PlaceList;
