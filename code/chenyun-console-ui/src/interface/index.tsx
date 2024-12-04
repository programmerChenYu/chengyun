// 对应数据库 Tag 表
export interface TagInterface {
    key: React.Key,
    tagName?: string,
    tagStatus?: number,
    createTime?: string,
    creator?: string,
    frequencyOfUse?: number
}

//对应数据库 User 表
export interface UserInterface {
    key: React.Key,
    username?: string,
    password?: string,
    role?: number,
    avatar?: string,
    email?: string,
    createdAt?: string,
    location?: string,
    nickname: string,
    userStatus?: number,
    information?: string,
    infoStatus?: number,
    reviewInfo?: string
}

// 对应数据库的 blog_post 表
export interface BlogPostInterface {
    key: React.Key,
    title?: string,
    content?: string,
    author?: string,
    status?: number,
    views?: number,
    likes?: number,
    bookmarksCount?: number,
    createdAt?: string,
    updatedAt?: string
}
