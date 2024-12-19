import {createBrowserRouter} from "react-router-dom";
import App from "../App.tsx";
import Home from "../pages/Home";
import TagManagement from "../pages/TagManagement";
import UserManagement from "../pages/UserManagement";
import BlogManagement from "../pages/BlogManagement";
import UserInfoAudit from "../pages/UserManagement/UserInfoAudit.tsx";
import AuditInfo from "../pages/UserManagement/AuditInfo.tsx";
import AuditedBlog from "../pages/BlogManagement/AuditedBlog.tsx";
import ReviewedBlog from "../pages/BlogManagement/ReviewedBlog.tsx";
import BlogInfo from "../pages/BlogManagement/BlogInfo";
import ServerError from "../pages/Errors/ServerError.tsx";
import Login from "../pages/Login";

const router = createBrowserRouter([
    {
        path: '/',
        element: <App />,
        children: [
            {
                index: true,
                element: <Home />
            },
            {
                path: 'user',
                element: <UserManagement />,
                children: [
                    {
                        path: 'audit',
                        element: <UserInfoAudit />,
                        children: [
                            {
                                path: 'info',
                                element: <AuditInfo />
                            }
                        ]
                    }
                ]
            },
            {
                path: 'tag',
                element: <TagManagement />
            },
            {
                path: 'blog',
                element: <BlogManagement />,
                children: [
                    {
                        path: 'audited',
                        element: <AuditedBlog />
                    },
                    {
                        path: 'reviewed',
                        element: <ReviewedBlog />
                    },
                ]
            },
            {
                path: 'blog-info',
                element: <BlogInfo />
            }
        ]
    },
    {
        path:'/login',
        element: <Login />
    },
    {
        path: '*',
        element: <ServerError />
    }
])

export {router}
