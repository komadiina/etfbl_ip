import { useEffect, useState } from "react";
import { acl } from "../config/acl.js";
import api from "../utils/api.js";


export const Home = () => {
  const [isSignedIn, setIsSignedIn] = useState(api.isSignedIn());

  useEffect(() => {


  }, []);

  if (isSignedIn) {
    return acl[utils.getUserType()];
  }
}
