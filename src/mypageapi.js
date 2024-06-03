export async function getUserData(token) {
  try {
    const response = await fetch(`${process.env.REACT_APP_API_URL}api/mypage`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
      },
    });
    return response.json();
  } catch (error) {
    console.error("유저 데이터 불러오기 실패", error);
    throw error;
  }
}

export async function updateUserData(token, name) {
  try {
    const response = await fetch(
      `${process.env.REACT_APP_API_URL}api/mypage/update`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify({
          "name": name,
          "description": "",
        }),
      }
    );
    const text = await response.text();
    if (text) {
      const data = JSON.parse(text);
      return data;
    }
  } catch (error) {
    console.error("유저 데이터 업데이트 실패", error);
    throw error;
  }
}

export async function postPetImageData(token, image) {
  const formData = new FormData();
  formData.append("image", image);

  try {
    await fetch(`${process.env.REACT_APP_API_URL}api/mypage/dog/image`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
      },
      body: formData,
    });
  } catch (error) {
    console.error("강아지 사진 업로드 실패", error);
    throw error;
  }
}

export async function postPetData(token, name, type, sex, birth) {
  try {
    await fetch(`${process.env.REACT_APP_API_URL}api/mypage/dog/detail`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        dogName: name,
        species: type,
        gender: sex,
        registrationNumber: birth,
      }),
    });
  } catch (error) {
    console.error("강아지 정보 업로드 실패", error);
    throw error;
  }
}
