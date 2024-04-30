import { useEffect, useState } from "react";

const useGeolocation = () => {
  const [currentMyLocation, setCurrentMyLocation] = useState({
    lat: 0,
    lng: 0,
  });
  const [locationLoading, setLocationLoading] = useState(false);

  const getCurPostion = () => {
    setLocationLoading(true);
    const success = (location) => {
      setCurrentMyLocation({
        lat: location.coords.latitude,
        lng: location.coords.longitude,
      });
      setLocationLoading(false);
    };

    const error = () => {
      setCurrentMyLocation({ lat: 35.1339867555, lng: 129.1055536386 });
      setLocationLoading(false);
    };

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(success, error);
    }
  };

  useEffect(() => {
    getCurPostion();
  }, []);

  return { currentMyLocation, locationLoading, getCurPostion };
};

export default useGeolocation;
